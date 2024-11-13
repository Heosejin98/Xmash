import { createFileRoute } from "@tanstack/react-router";
import { AddGame } from "@/widgets/game/add-game";

export const Route = createFileRoute("/_auth/add")({
  component: () => <AddGame step="matchType" />,
});
