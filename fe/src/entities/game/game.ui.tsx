import { Route } from "@/pages/_layout.game";
import { GameType, MatchType } from "@/shared/api/game";
import {
  Avatar,
  AvatarFallback,
  AvatarImage,
  Input,
  Table,
  TableBody,
  TableCell,
  TableRow,
} from "@/shared/ui";
import { useQuery } from "@tanstack/react-query";
import { useNavigate } from "@tanstack/react-router";
import { useMemo, useState } from "react";
import { GameQueries } from "./game.queries";
import { GameTypeTabs } from "./gameType.tabs.ui";
import { MatchTypeTabs } from "./matchType.tabs.ui";

export function GameList() {
  const { gameType, matchType } = Route.useSearch();
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

  const tableRows = useMemo(() => {
    return data
      ?.filter((d) => matchType === "all" || d.matchType === matchType)
      ?.filter(
        (d) =>
          searchValue === "" ||
          d.winTeam.some((p) => p.userName.includes(searchValue)) ||
          d.loseTeam.some((p) => p.userName.includes(searchValue))
      );
  }, [data, matchType, searchValue]);

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

        <Table>
          <TableBody>
            {tableRows?.length ? (
              tableRows?.map((record) => (
                <TableRow
                  key={`${record.matchTime}: ${record.winTeam.toString()}`}
                  className="flex gap-2 items-center justify-between"
                >
                  <TableCell className="flex gap-1">
                    {record.winTeam.map((player, idx) => (
                      <Avatar key={`${record.matchTime}: ${player.userName}${idx}`}>
                        <AvatarImage src={player.profileUrl ?? ""} alt={player.userName} />
                        <AvatarFallback>{player.userName.slice(1, 3)}</AvatarFallback>
                      </Avatar>
                    ))}
                  </TableCell>

                  <TableCell className="flex gap-2">
                    <div>{record.winnerScore}</div>:<div>{record.loserScore}</div>
                  </TableCell>

                  <TableCell className="flex gap-1">
                    {record.loseTeam.map((player, idx) => (
                      <Avatar key={`${record.matchTime}: ${player.userName}${idx}`}>
                        <AvatarImage src={player.profileUrl ?? ""} alt={player.userName} />
                        <AvatarFallback>{player.userName.slice(1, 3)}</AvatarFallback>
                      </Avatar>
                    ))}
                  </TableCell>
                </TableRow>
              ))
            ) : (
              <TableRow>
                <TableCell className="h-24 text-center">No results.</TableCell>
              </TableRow>
            )}
          </TableBody>
        </Table>
      </div>
    </div>
  );
}
