import { LeaderBoardList } from "@/entities/leaderBoard/leaderBoard.ui";
import { createLazyFileRoute } from "@tanstack/react-router";

export const Route = createLazyFileRoute("/leader-board")({
  component: Index,
});

function Index() {
  return <LeaderBoardList></LeaderBoardList>;
}
