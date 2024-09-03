import { LeaderBoardList } from "@/entities/leaderBoard/leaderBoard.ui";
import { createFileRoute } from "@tanstack/react-router";
import { z } from "zod";

const UserSearchSchema = z.object({
  username: z.string().optional(),
});
export type UserSearch = z.infer<typeof UserSearchSchema>;

export const Route = createFileRoute("/leader-board")({
  component: Index,
  validateSearch: UserSearchSchema,
});

function Index() {
  return <LeaderBoardList></LeaderBoardList>;
}
