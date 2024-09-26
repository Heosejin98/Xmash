import { Avatar, AvatarFallback, AvatarImage, Input } from "@/shared/ui";
import { useQuery } from "@tanstack/react-query";
import { useState } from "react";
import { GameQueries } from "./game.queries";
import { MatchTypeTabs } from "./matchType.tabs.ui";
import { GameTypeTabs } from "./gameType.tabs.ui";
import { GameType, MatchType } from "@/shared/api/game";

export function GameList() {
  const [searchValue, setSearchValue] = useState("");
  const [gameType, setGameType] = useState<GameType>("normal");
  const [matchType, setMatchType] = useState<MatchType | "all">("all");

  const { data } = useQuery(
    GameQueries.gameQuery({
      gameType: gameType,
      matchType: "single",
    })
  );

  return (
    <div className="w-full p-3">
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

        {data
          ?.filter((d) => d.matchType)
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
      </div>
    </div>
  );
}
