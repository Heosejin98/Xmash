import { Avatar, AvatarFallback, AvatarImage, Input } from "@/shared/ui";
import { useQuery } from "@tanstack/react-query";
import { useState } from "react";
import { GameQueries } from "./game.queries";
import { MatchTypeTabs } from "./matchType.tabs.ui";
import { GameTypeTabs } from "./gameType.tabs.ui";
import { getRouteApi, useNavigate } from "@tanstack/react-router";
import { Route } from "@/pages/_layout.game";
import { GameType, MatchType } from "@/shared/api/game";

export function GameList() {
  const { gameType, matchType } = getRouteApi("/_layout/game").useSearch();
  const navigate = useNavigate({ from: Route.fullPath });

  const setGameType = (gameType: GameType) => {
    navigate({ search: { gameType, matchType } });
  };

  const setMatchType = (matchType: MatchType) => {
    navigate({ search: { matchType, gameType } });
  };

  const [searchValue, setSearchValue] = useState("");

  const { data } = useQuery(
    GameQueries.gameQuery({
      gameType: gameType,
      matchType: matchType,
    })
  );

  return (
    <div className="w-full p-3 flex flex-col mb-nav">
      <div className="flex items-center py-4">
        <Input
          placeholder="Filter names..."
          value={searchValue}
          onChange={(e) => setSearchValue(e.target.value)}
          className="max-w-sm"
        />
      </div>
      <div className="rounded-md border">
        <GameTypeTabs onChange={setGameType} type={gameType} />
        <MatchTypeTabs onChange={setMatchType} type={matchType} />

        <article className="">
          {data
            ?.filter((d) => matchType === "all" || d.matchType === matchType)
            ?.filter(
              (d) =>
                searchValue === "" ||
                d.winTeam.some((p) => p.userName.includes(searchValue)) ||
                d.loseTeam.some((p) => p.userName.includes(searchValue))
            )
            ?.map((record) => (
              <div
                key={`${record.matchTime}: ${record.winTeam.toString()}`}
                className="flex items-center border-b p-4 justify-between"
              >
                <div>
                  {record.winTeam.map((player) => (
                    <Avatar key={`${record.matchTime}: ${player.userName}`} className="mr-4">
                      <AvatarImage src={player.profileUrl ?? ""} alt={player.userName} />
                      <AvatarFallback>{player.userName.slice(1, 3)}</AvatarFallback>
                    </Avatar>
                  ))}
                </div>

                <div className="flex gap-2">
                  <div>{record.winnerScore}</div>:<div>{record.loserScore}</div>
                </div>

                <div>
                  {record.loseTeam.map((player) => (
                    <Avatar key={`${record.matchTime}: ${player.userName}`} className="mr-4">
                      <AvatarImage src={player.profileUrl ?? ""} alt={player.userName} />
                      <AvatarFallback>{player.userName.slice(1, 3)}</AvatarFallback>
                    </Avatar>
                  ))}
                </div>
              </div>
            ))}
        </article>
      </div>
    </div>
  );
}
