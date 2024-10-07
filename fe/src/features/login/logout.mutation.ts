import { useAuthStore } from '@/entities/user/user.store';
import { AuthService } from '@/shared/api/auth';
import {
  DefaultError,
  UseMutationOptions,
  useMutation,
} from '@tanstack/react-query';

export function useLogoutMutation(
  options?: Pick<
    UseMutationOptions<
      Awaited<ReturnType<typeof AuthService.logoutMutation>>,
      DefaultError,
      void,
      unknown
    >,
    'mutationKey' | 'onMutate' | 'onSuccess' | 'onError' | 'onSettled'
  >,
) {
  const {
    mutationKey = [],
    onMutate,
    onSuccess,
    onError,
    onSettled,
  } = options || {}
  const { setAuthenticated, setUser } = useAuthStore();


  return useMutation({
    mutationKey: ['session', 'logout', ...mutationKey],

    mutationFn: async () => await AuthService.logoutMutation(),

    onMutate,

    onSuccess: async (response, variables, context) => {
      setAuthenticated(false)
      setUser(null)
      await onSuccess?.(response, variables, context)
    },

    onError,

    onSettled,
  })
}
