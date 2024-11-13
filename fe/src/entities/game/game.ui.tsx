import { Route } from "@/pages/_layout/game";
import { GameDto, MatchType } from "@/shared/api/game";
import { createFuzzyMatcher } from "@/shared/lib/search";
import { cn } from "@/shared/lib/utils";
import {
  Avatar,
  AvatarFallback,
  AvatarImage,
  Button,
  Drawer,
  DrawerContent,
  DrawerDescription,
  DrawerFooter,
  DrawerHeader,
  DrawerTitle,
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
import { MatchTypeTabs } from "./matchType.tabs.ui";

export function GameList() {
  const { matchType } = Route.useSearch();
  const navigate = useNavigate({ from: Route.fullPath });

  const setMatchType = (matchType: MatchType) => {
    navigate({ search: { matchType } });
  };

  const [searchValue, setSearchValue] = useState("");
  const [isDrawerOpen, setIsDrawerOpen] = useState(false);

  const { data } = useQuery(
    GameQueries.gameQuery({
      matchType: matchType,
    })
  );

  const tableRows = useMemo(() => {
    const reg = createFuzzyMatcher(searchValue);

    return data
      ?.filter((d) => matchType === "all" || d.matchType === matchType)
      ?.filter(
        (d) =>
          searchValue === "" ||
          d.winTeam.some((p) => reg.test(p.userName)) ||
          d.loseTeam.some((p) => reg.test(p.userName))
      );
  }, [data, matchType, searchValue]);

  const [target, setTarget] = useState<GameDto>();
  const goToAmendPage = () => {
    if (!target) return;
    navigate({
      to: "/game/$gameId",
      params: { gameId: target.idx.toString() },
      search: {
        matchType: target.matchType as MatchType,
        homeTeam: target.winTeam.map((p) => p.userId),
        awayTeam: target.loseTeam.map((p) => p.userId),
        homeScore: target.winnerScore,
        awayScore: target.loserScore,
      },
    });
  };

  return (
    <>
      <div className="w-full p-3 flex flex-col">
        <div className="flex items-center py-4">
          <Input
            placeholder="검색할 이름..."
            value={searchValue}
            onChange={(e) => setSearchValue(e.target.value)}
            className="max-w-sm"
            type="search"
          />
        </div>
        <div className="rounded-md border">
          <MatchTypeTabs onChange={setMatchType} type={matchType} />

          <Table>
            <TableBody>
              {tableRows?.length ? (
                tableRows?.map((record) => (
                  <TableRow
                    key={`${record.matchTime}: ${record.winTeam.toString()}`}
                    className={cn("flex items-center justify-between w-full", {
                      "animate-pulse":
                        new Date().getTime() - record.matchTime.getTime() < 10 * 60 * 1000,
                    })}
                    onDoubleClick={() => {
                      if (new Date().getTime() - record.matchTime.getTime() > 10 * 60 * 1000) {
                        return;
                      }

                      setIsDrawerOpen(true);
                      setTarget(record);
                    }}
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

      <Drawer open={isDrawerOpen} onOpenChange={setIsDrawerOpen}>
        <DrawerContent>
          <DrawerHeader>
            <DrawerTitle>수정 하시겠습니까?</DrawerTitle>
            <DrawerDescription>
              {target?.winTeam.map((p) => p.userName).join(", ")} vs{" "}
              {target?.loseTeam.map((p) => p.userName).join(", ")}
            </DrawerDescription>
          </DrawerHeader>
          <DrawerFooter>
            <Button onClick={goToAmendPage}>수정</Button>
            <Button variant="outline" onClick={() => setIsDrawerOpen(false)}>
              취소
            </Button>
          </DrawerFooter>
        </DrawerContent>
      </Drawer>
    </>
  );
}
