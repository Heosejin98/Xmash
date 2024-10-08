import { LeaderBoardList } from "@/entities/ranking/ranking.ui";
import { createFileRoute } from "@tanstack/react-router";
import { z } from "zod";

export const UserSearchSchema = z.object({
  username: z.string().optional(),
});
export type UserSearch = z.infer<typeof UserSearchSchema>;

export const Route = createFileRoute("/_layout/ranking")({
  component: Index,
  validateSearch: UserSearchSchema,
});

function Index() {
  return <LeaderBoardList></LeaderBoardList>;
}
