import DepthHeader from "@/app/depthHeader.ui";
import { RankingQueries } from "@/entities/ranking/ranking.queries";
import { UserQueries } from "@/entities/user/user.queries";
import { MatchType } from "@/shared/api/game";
import { cn } from "@/shared/lib/utils";
import {
  Avatar,
  AvatarFallback,
  AvatarImage,
  Button,
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
  Label,
  ScrollArea,
} from "@/shared/ui";
import { useQuery } from "@tanstack/react-query";
import { X } from "lucide-react";
import { Dispatch, forwardRef, SetStateAction, useEffect, useMemo, useState } from "react";

const UserListInput = forwardRef(
  (
    {
      value,
      onChange,
      requiredPlayers,
      className,
    }: {
      value: string[];
      requiredPlayers: number;
      onChange: Dispatch<SetStateAction<string[]>>;
      className?: string;
    },
    _
  ) => {
    const { data = [] } = useQuery(UserQueries.userAllQuery());

    return (
      <div className={cn("flex gap-2", className)}>
        {data
          .filter((user) => value.includes(user.userId))
          .map((user) => (
            <div className={cn("relative inline-block", className)} key={user.userId}>
              <Avatar>
                <AvatarImage src={user.profileUrl ?? ""} alt={user.userName} />
                <AvatarFallback>{user.userName.slice(1, 3)}</AvatarFallback>
              </Avatar>
              <Button
                type="button"
                variant="destructive"
                size="icon"
                className="absolute -top-1 right-1 h-4 w-4 rounded-full"
                onClick={() => onChange(value.filter((v) => v !== user.userId))}
              >
                <X className="h-3 w-3"></X>
              </Button>
            </div>
          ))}
        {Array.from({ length: requiredPlayers - value.length }).map((_, i) => (
          <Avatar key={i}>
            <AvatarImage src={""} alt={""} />
            <AvatarFallback>{"?"}</AvatarFallback>
          </Avatar>
        ))}
      </div>
    );
  }
);

interface Props {
  onNext: (players: string[]) => void;
  onPrev?: (players: string[]) => void;
  matchType: MatchType;
  teamType: "home" | "away";
  team?: string[];
  exclude?: string[];
}

export const PlayersInputForm = ({ onNext, onPrev, matchType, team, exclude, teamType }: Props) => {
  const [players, onChangePlayers] = useState<string[]>(team ?? []);
  const requiredPlayers = useMemo(() => (matchType === "single" ? 1 : 2), [matchType]);
  const { data } = useQuery(RankingQueries.rankingQuery(matchType));

  const onClickPlayer = (userId: string) => {
    if (players.includes(userId)) {
      onChangePlayers(players.filter((v) => v !== userId));
      return;
    }

    if (requiredPlayers === players.length + 1) {
      onNext([...players, userId]);
      return;
    }

    onChangePlayers([...players, userId]);
  };

  useEffect(() => {
    onChangePlayers(team ?? []);
  }, [teamType]);

  return (
    <>
      <DepthHeader
        onPrev={() => {
          onPrev?.(players);
        }}
      ></DepthHeader>
      <Card className="m-4 h-full">
        <CardHeader>
          <CardTitle>{teamType.toUpperCase()} 선수 입력</CardTitle>
          <CardDescription>선수를 입력해 주세요.</CardDescription>
        </CardHeader>
        <CardContent className="flex flex-col gap-4 flex-1">
          <div className="items-start flex-col flex gap-4">
            <UserListInput
              requiredPlayers={requiredPlayers}
              value={players}
              onChange={onChangePlayers}
            ></UserListInput>
          </div>

          {requiredPlayers === players.length && (
            <Button type="button" className="h-10 w-full" onClick={() => onNext(players)}>
              다음
            </Button>
          )}

          {requiredPlayers !== players.length && (
            <>
              <Label>선수 목록</Label>

              <ScrollArea className="h-80 border rounded-lg">
                {data.map((user) => (
                  <button
                    type="button"
                    disabled={exclude?.includes(user.userId)}
                    className={cn(
                      "relative my-2 p-2 flex gap-3 w-full disabled:cursor-not-allowed disabled:opacity-50 disabled:bg-gray-200",
                      {
                        "bg-gray-100": players.includes(user.userId),
                      }
                    )}
                    key={user.userId}
                    onClick={() => onClickPlayer(user.userId)}
                  >
                    <Avatar>
                      <AvatarImage src={""} alt={user.userName ?? ""} />
                      <AvatarFallback>{user.userName?.slice(1, 3)}</AvatarFallback>
                    </Avatar>
                    <div className="flex flex-col gap-1">
                      <span className="text-sm font-bold">{user.userName}</span>
                      <span className="text-xs">{user.tier}</span>
                    </div>
                  </button>
                ))}
              </ScrollArea>
            </>
          )}
        </CardContent>
      </Card>
    </>
  );
};
