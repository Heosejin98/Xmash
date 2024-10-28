import { GameInfoForm } from "@/features/game/add-game-form.ui";
import { createFileRoute } from "@tanstack/react-router";
import { Card } from "@/shared/ui";

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
      <Card className="p-4 m-4">
        <GameInfoForm></GameInfoForm>
      </Card>
    </>
  );
};

export const Route = createFileRoute("/_auth/add")({
  component: () => <AddGame />,
});
