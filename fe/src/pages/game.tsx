import { GameWidget } from "@/widgets/record/record.ui";
import { createFileRoute } from "@tanstack/react-router";
import { UserSearchSchema } from "./ranking";

export const Route = createFileRoute("/game")({
  component: GameWidget,
  validateSearch: UserSearchSchema,
});
