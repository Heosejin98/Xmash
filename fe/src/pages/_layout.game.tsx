import { GameWidget } from "@/widgets/record/record.ui";
import { createFileRoute } from "@tanstack/react-router";
import { UserSearchSchema } from "./_layout.ranking";

export const Route = createFileRoute("/_layout/game")({
  component: GameWidget,
  validateSearch: UserSearchSchema,
});
