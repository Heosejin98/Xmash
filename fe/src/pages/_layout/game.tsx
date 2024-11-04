import { GameWidget } from "@/widgets/record/record.ui";
import { createFileRoute } from "@tanstack/react-router";
import { z } from "zod";
import { MatchType } from "@/shared/api/game";

const searchSchema = z.object({
  matchType: MatchType.default("all"),
});

export const Route = createFileRoute("/_layout/game")({
  component: GameWidget,
  validateSearch: searchSchema,
});
