import { useAuthStore } from '@/entities/user/user.store';
import { AuthService, LoginUserDto } from '@/shared/api/auth';
import {
  DefaultError,
  UseMutationOptions,
  useMutation,
} from '@tanstack/react-query';



export function useLoginMutation(
  options?: Pick<
    UseMutationOptions<
      Awaited<ReturnType<typeof AuthService.loginUserMutation>>,
      DefaultError,
      LoginUserDto,
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
    mutationKey: ['session', 'login-user', ...mutationKey],

    mutationFn: async (loginUserDto: LoginUserDto) => await AuthService.loginUserMutation({ loginUserDto }),

    onMutate,

    onSuccess: async (response, variables, context) => {
      const user = response.data

      setAuthenticated(true)
      setUser(user)

      await onSuccess?.(response, variables, context)
    },

    onError,

    onSettled,
  })
}
