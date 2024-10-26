import { GameInfoForm } from "@/features/game/add-game-form.ui";
import { createFileRoute } from "@tanstack/react-router";

const AddGame = () => {
  // const funnel = useFunnel<{
  //   homePlayer: HomePlayerInput;
  //   awayPlayer: AwayPlayerInput;
  //   homeScore: HomeScoreInput;
  //   awayScore: AwayScoreInput;
  //   gameType: GameTypeInput;
  // }>({
  //   id: "add-game",
  //   initial: {
  //     step: "homePlayer",
  //     context: {},
  //   },
  // });

  return (
    <>
      <GameInfoForm></GameInfoForm>
    </>
  );
};

export const Route = createFileRoute("/_auth/add")({
  component: () => <AddGame />,
});
