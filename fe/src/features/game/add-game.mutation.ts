import { CreateGameDto, GameService } from '@/shared/api/game';
import {
  DefaultError,
  UseMutationOptions,
  useMutation,
} from '@tanstack/react-query';

export function useGameMutation(
  options?: Pick<
    UseMutationOptions<
      Awaited<ReturnType<typeof GameService.gameMutation>>,
      DefaultError,
      CreateGameDto,
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
    mutationKey: ['game', 'create-game', ...mutationKey],

    mutationFn: async (createGameDto: CreateGameDto) => GameService.gameMutation({ createGameDto }),

    onMutate,

    onSuccess,

    onError,

    onSettled,
  })
}
