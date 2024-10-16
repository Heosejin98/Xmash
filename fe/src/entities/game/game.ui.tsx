import { Route } from "@/pages/_layout/game";
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
    <div className="w-full p-3 flex flex-col">
      <div className="flex items-center py-4">
        <Input
          placeholder="검색할 이름..."
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
                  className="flex items-center justify-between w-full"
                >
                  <TableCell className="flex gap-1">
                    {record.winTeam.map((player, idx) => (
                      <div
                        key={`${record.matchTime}: ${player.userName}${idx}`}
                        className="flex flex-col items-center justify-center"
                      >
                        <Avatar>
                          <AvatarImage src={player.profileUrl ?? ""} alt={player.userName} />
                          <AvatarFallback>{player.userName.slice(1, 3)}</AvatarFallback>
                        </Avatar>
                        {/* <span className="text-xs">{player.userName.slice(1, 3)}</span> */}
                      </div>
                    ))}
                  </TableCell>

                  <TableCell className="flex gap-2">
                    <div className="flex flex-col items-center gap-1">
                      <div>
                        <span className="bold text-lg">{record.winnerScore}</span> vs{" "}
                        <span className="bold text-lg">{record.loserScore}</span>
                      </div>
                      <div className="flex flex-col items-center">
                        <span className="w-fit text-center text-[10px]/[1] text-gray-500">
                          {record.matchTime.toLocaleDateString(undefined, {
                            weekday: "narrow",
                            month: "numeric",
                            day: "numeric",
                          })}
                        </span>
                        <span className="w-fit text-center text-sm">
                          {record.matchTime.toLocaleTimeString(undefined, {
                            hour12: false,
                            hour: "numeric",
                            minute: "2-digit",
                          })}
                        </span>
                      </div>
                    </div>
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
