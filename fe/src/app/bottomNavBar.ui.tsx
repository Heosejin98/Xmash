import { Link } from "@tanstack/react-router";
import { Gamepad2, Medal, Plus, Trophy } from "lucide-react";
import { CSSProperties } from "react";

const activeProps: {
  style: CSSProperties;
} = {
  style: {
    color: "var(--accent)",
    fontWeight: "bold",
  },
};

const RankingLink = () => {
  return (
    <Link
      to="/ranking"
      className="nav-link  w-14 h-14 flex flex-col items-center group"
      search={{
        matchType: "single",
      }}
      activeOptions={{ includeSearch: false }}
      activeProps={activeProps}
    >
      <Medal className="group-data-[status=active]:animate-bounce" />
      <span className="text-xs">순위</span>
    </Link>
  );
};

const GameLink = () => {
  return (
    <Link
      to="/game"
      className="nav-link  w-14 h-14 flex flex-col items-center group"
      activeOptions={{ includeSearch: false }}
      activeProps={activeProps}
      search={{
        matchType: "all",
      }}
    >
      <Gamepad2 className="group-data-[status=active]:animate-bounce" />
      <span className="text-xs">전적</span>
    </Link>
  );
};

const AddLink = () => {
  return (
    <Link
      to="/add"
      className="nav-link  w-14 h-14 flex flex-col items-center group"
      activeOptions={{ includeSearch: false }}
      activeProps={activeProps}
    >
      <Plus className="group-data-[status=active]:animate-bounce"></Plus>
      <span className="text-xs">경기 등록</span>
    </Link>
  );
};

const TournamentLink = () => {
  return (
    <Link
      to="/tournament"
      disabled
      className="nav-link w-14 h-14 flex flex-col items-center group text-gray-300"
      activeOptions={{ includeSearch: false }}
      activeProps={activeProps}
    >
      <Trophy className="group-data-[status=active]:animate-bounce"></Trophy>
      <span className="text-xs">대회 준비중</span>
    </Link>
  );
};

const BottomNavBar = () => {
  return (
    <footer>
      <nav className="flex justify-between p-4 bottom-0 w-full h-nav z-10 bg-muted fixed max-w-[600px]">
        <RankingLink />
        <TournamentLink />
        <AddLink />
        <GameLink />
      </nav>
    </footer>
  );
};

export default BottomNavBar;
