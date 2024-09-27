import { AddGameButton } from "@/features/game/game.ui";
import { Link } from "@tanstack/react-router";

const activeProps = { style: { fontWeight: "bold" } };

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

const HomeLink = () => {
  return (
    <Link to="/" className="nav-link" activeProps={activeProps}>
      Home
    </Link>
  );
};

const NavBar = () => {
  return (
    <nav className="flex justify-between p-4 fixed bottom-0 w-full h-nav">
      <RankingLink />
      {/* <HomeLink /> */}
      <AddGameButton></AddGameButton>
      <GameLink />
    </nav>
  );
};

export default NavBar;
