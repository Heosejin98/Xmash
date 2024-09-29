import { AddGameButton } from "@/features/game/game.ui";
import { Link } from "@tanstack/react-router";
import { CSSProperties } from "react";

const activeProps: {
  style: CSSProperties;
} = {
  style: {
    color: "var(--accent)",
  },
};

const RankingLink = () => {
  return (
    <Link to="/ranking" className="nav-link" activeProps={activeProps}>
      Ranking
    </Link>
  );
};

const GameLink = () => {
  return (
    <Link to="/game" className="nav-link" activeProps={activeProps}>
      Game
    </Link>
  );
};

const BottomNavBar = () => {
  return (
    <nav className="flex justify-between p-4 fixed left-0 bottom-0 w-full h-nav z-10 bg-muted">
      <RankingLink />
      <AddGameButton></AddGameButton>
      <GameLink />
    </nav>
  );
};

export default BottomNavBar;
