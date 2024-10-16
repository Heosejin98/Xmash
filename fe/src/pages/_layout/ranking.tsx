import { LeaderBoardList } from "@/entities/ranking/ranking.ui";
import { MatchType } from "@/shared/api/game";
import { createFileRoute } from "@tanstack/react-router";
import { z } from "zod";

export const UserSearchSchema = z.object({
  username: z.string().optional(),
  matchType: MatchType.default("single"),
});
export type UserSearch = z.infer<typeof UserSearchSchema>;

export const Route = createFileRoute("/_layout/ranking")({
  component: Index,
  validateSearch: UserSearchSchema,
});

function Index() {
  return <LeaderBoardList></LeaderBoardList>;
}
