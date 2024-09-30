import { GameWidget } from "@/widgets/record/record.ui";
import { createFileRoute } from "@tanstack/react-router";
import { z } from "zod";
import { GameType, MatchType } from "@/shared/api/game";

const searchSchema = z.object({
  gameType: GameType,
  matchType: MatchType,
});

export const Route = createFileRoute("/_layout/game")({
  component: GameWidget,
  validateSearch: searchSchema,
});
