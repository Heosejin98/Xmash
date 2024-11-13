import { AwayTeamInput, HomeTeamInput, MatchTypeInput, ScoresInput } from "@/features/game/context";
import { MatchTypeInputButton } from "@/features/game/matchTypeInput.ui";
import { PlayersInputForm } from "@/features/game/playersInput.ui";
import { ScoreInputForm } from "@/features/game/scoreInput.ui";
import { CreateGameDto } from "@/shared/api/game";
import { useParams } from "@tanstack/react-router";
import { useFunnel } from "@use-funnel/browser";

export const AddGame = ({
  init,
  step,
}: {
  init?: CreateGameDto;
  step?: "homeTeam" | "awayTeam" | "matchType" | "scores";
}) => {
  const { gameId } = useParams({
    strict: false,
  });

  const funnel = useFunnel<{
    matchType: MatchTypeInput;
    homeTeam: HomeTeamInput;
    awayTeam: AwayTeamInput;
    scores: ScoresInput;
  }>({
    id: gameId ?? "add-game",
    initial: {
      step: step ?? "matchType",
      context: {
        matchType: init?.matchType ?? "single",
        awayScore: init?.awayScore ?? 0,
        homeScore: init?.homeScore ?? 0,
        homeTeam: init?.homeTeam ?? [],
        awayTeam: init?.awayTeam ?? [],
      },
    },
  });

  switch (funnel.step) {
    case "matchType":
      return (
        <MatchTypeInputButton
          onNext={(matchType) => funnel.history.replace("homeTeam", { matchType })}
          onPrev={() => {
            funnel.history.back();
          }}
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
          onPrev={(homeTeam: string[]) =>
            funnel.history.replace("matchType", { ...funnel.context, homeTeam })
          }
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
          awayScore={funnel.context.awayScore}
          homeScore={funnel.context.homeScore}
          onPrev={() => funnel.history.replace("awayTeam", { ...funnel.context })}
        ></ScoreInputForm>
      );
  }
};
