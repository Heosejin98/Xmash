import { UpdateGameDto, GameService } from "@/shared/api/game";
import { DefaultError, UseMutationOptions, useMutation } from "@tanstack/react-query";
import { AxiosError } from "axios";

export function useUpdateGameMutation(
  options?: Pick<
    UseMutationOptions<
      Awaited<ReturnType<typeof GameService.updateGameMutation>>,
      AxiosError<DefaultError>,
      {
        gameId: string;
        updateGameDto: UpdateGameDto;
      },
      unknown
    >,
    "mutationKey" | "onMutate" | "onSuccess" | "onError" | "onSettled"
  >
) {
  const { mutationKey = [], onMutate, onSuccess, onError, onSettled } = options || {};

  return useMutation({
    mutationKey: ["game", "update-game", ...mutationKey],

    mutationFn: async ({ gameId, updateGameDto }) =>
      GameService.updateGameMutation({ gameId, updateGameDto }),

    onMutate,

    onSuccess,

    onError,

    onSettled,
  });
}
