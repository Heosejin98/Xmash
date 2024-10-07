import { AuthService, PasswordResetDto } from '@/shared/api/auth';
import {
DefaultError,
UseMutationOptions,
useMutation,
} from '@tanstack/react-query';

export function useResetPasswordMutation(
  options?: Pick<
    UseMutationOptions<
      Awaited<ReturnType<typeof AuthService.passwordResetMutation>>,
      DefaultError,
      PasswordResetDto,
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
  return useMutation({
    mutationKey: ['session', 'reset-password', ...mutationKey],

    mutationFn: async (passwordResetDto: PasswordResetDto) => await AuthService.passwordResetMutation({ passwordResetDto }),

    onMutate,

    onSuccess: async (response, variables, context) => {
      await onSuccess?.(response, variables, context)
    },

    onError,

    onSettled,
  })
}
