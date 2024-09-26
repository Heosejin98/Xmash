import { Avatar, AvatarFallback, AvatarImage, Input } from "@/shared/ui";
import { useQuery } from "@tanstack/react-query";
import { useState } from "react";
import { GameQueries } from "./game.queries";
import { MatchTypeTabs } from "./matchType.tabs.ui";
import { GameTypeTabs } from "./gameType.tabs.ui";

export function GameList() {
  const [searchValue, setSearchValue] = useState("");
  const { data } = useQuery(
    GameQueries.gameQuery({
      gameType: "normal",
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
        <GameTypeTabs />
        <MatchTypeTabs />

        {data?.map((record, idx) => (
          <div key={idx} className="flex items-center border-b p-4 justify-between">
            <div>
              {record.winTeam.map((player) => (
                <Avatar className="mr-4">
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
                <Avatar className="mr-4">
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
