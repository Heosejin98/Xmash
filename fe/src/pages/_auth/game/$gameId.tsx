import { createFileRoute } from "@tanstack/react-router";
import { AddGame } from "@/widgets/game/add-game";
import { UpdateGameDto } from "@/shared/api/game";

const UpdateGame = () => {
  const params = Route.useSearch();

  return <AddGame init={{ ...params }} step="scores"></AddGame>;
};

export const Route = createFileRoute("/_auth/game/$gameId")({
  validateSearch: UpdateGameDto,
  component: UpdateGame,
});
