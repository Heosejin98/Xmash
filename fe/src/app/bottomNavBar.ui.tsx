import { useAuthStore } from "@/entities/user/user.store";
import { AddGameButton } from "@/features/game/add-game.ui";
import { Link } from "@tanstack/react-router";
import { CSSProperties } from "react";
import { Gamepad2, Medal } from "lucide-react";

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
        gameType: "normal",
        matchType: "all",
      }}
    >
      <Gamepad2 className="group-data-[status=active]:animate-bounce" />
      <span className="text-xs">전적</span>
    </Link>
  );
};

const BottomNavBar = () => {
  const store = useAuthStore();
  return (
    <nav className="flex justify-between p-4 left-0 bottom-0 w-full h-nav z-10 bg-muted fixed">
      <RankingLink />
      {store.isAuthenticated && <AddGameButton></AddGameButton>}
      <GameLink />
    </nav>
  );
};

export default BottomNavBar;
