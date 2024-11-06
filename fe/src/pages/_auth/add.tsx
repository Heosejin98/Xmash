import { AwayTeamInput, HomeTeamInput, MatchTypeInput, ScoresInput } from "@/features/game/context";
import { MatchTypeInputButton } from "@/features/game/matchTypeInput.ui";
import { PlayersInputForm } from "@/features/game/playersInput.ui";
import { ScoreInputForm } from "@/features/game/scoreInput.ui";
import { createFileRoute } from "@tanstack/react-router";
import { useFunnel } from "@use-funnel/browser";

const AddGame = () => {
  const funnel = useFunnel<{
    matchType: MatchTypeInput;
    homeTeam: HomeTeamInput;
    awayTeam: AwayTeamInput;
    scores: ScoresInput;
  }>({
    id: "add-game",
    initial: {
      step: "matchType",
      context: {},
    },
  });

  switch (funnel.step) {
    case "matchType":
      return (
        <MatchTypeInputButton
          onNext={(matchType) => funnel.history.push("homeTeam", { matchType })}
          onPrev={() => funnel.history.back()}
        ></MatchTypeInputButton>
      );
    case "homeTeam":
      return (
        <PlayersInputForm
          key="home"
          matchType={funnel.context.matchType}
          exclude={funnel.context.awayTeam}
          teamType="home"
          team={funnel.context.homeTeam}
          onNext={(players) =>
            funnel.history.replace("awayTeam", { ...funnel.context, homeTeam: players })
          }
          onPrev={() => funnel.history.back()}
        ></PlayersInputForm>
      );
    case "awayTeam":
      return (
        <PlayersInputForm
          key="away"
          matchType={funnel.context.matchType}
          exclude={funnel.context.homeTeam}
          teamType="away"
          team={funnel.context.awayTeam}
          onNext={(players) =>
            funnel.history.replace("scores", { ...funnel.context, awayTeam: players })
          }
          onPrev={(awayTeam: string[]) =>
            funnel.history.replace("homeTeam", { ...funnel.context, awayTeam })
          }
        ></PlayersInputForm>
      );
    case "scores":
      return (
        <ScoreInputForm
          matchType={funnel.context.matchType}
          awayTeam={funnel.context.awayTeam}
          homeTeam={funnel.context.homeTeam}
          onPrev={() => funnel.history.replace("awayTeam", { ...funnel.context })}
        ></ScoreInputForm>
      );
  }
};

export const Route = createFileRoute("/_auth/add")({
  component: () => <AddGame />,
});