import {
  Button,
  Drawer,
  DrawerClose,
  DrawerContent,
  DrawerDescription,
  DrawerFooter,
  DrawerHeader,
  DrawerTitle,
  DrawerTrigger,
  Label,
} from "@/shared/ui";
import { Switch } from "@/shared/ui/switch";
import { useFunnel } from "@use-funnel/browser";
import { Plus } from "lucide-react";
import type { 게임타입입력, 점수입력, 플레이어입력 } from "./game.context";
import { GameTypeInput } from "./gameType.ui";
import { PlayerInput } from "./player.ui";

export function AddGameButton() {
  const funnel = useFunnel<{
    게임타입입력: 게임타입입력;
    플레이어입력: 플레이어입력;
    점수입력: 점수입력;
  }>({
    id: "game-funnel",
    initial: {
      step: "게임타입입력",
      context: {},
    },
  });

  return (
    <Drawer>
      <DrawerTrigger asChild>
        <Button className="rounded-full p-0 w-10 h-10" variant="outline">
          <Plus></Plus>
        </Button>
      </DrawerTrigger>
      <DrawerContent>
        <div className="mx-auto w-full max-w-sm">
          <DrawerHeader>
            <DrawerTitle>{funnel.step}</DrawerTitle>
            <DrawerDescription>cancle</DrawerDescription>
          </DrawerHeader>
          <div className="p-4 pb-0">
            <funnel.Render
              게임타입입력={({ history }) => (
                <GameTypeInput
                  onNext={(gameType, matchType) =>
                    history.push("플레이어입력", { gameType, matchType })
                  }
                />
              )}
              플레이어입력={({ history }) => (
                <PlayerInput
                  onPrev={() => history.back()}
                  onNext={(winTeam, loseTeam) => history.push("점수입력", { winTeam, loseTeam })}
                />
              )}
              점수입력={({ context }) => (
                <div>
                  {context.winTeam.map((team) => (
                    <div>{team}</div>
                  ))}
                  {context.loseTeam.map((team) => (
                    <div>{team}</div>
                  ))}
                  {context.gameType}
                  {context.matchType}
                  <Label>
                    <span>승자 </span>
                    <Switch></Switch>
                  </Label>
                </div>
              )}
            ></funnel.Render>
          </div>
          <DrawerFooter>
            <Button>Submit </Button>
            <DrawerClose asChild>
              <Button variant="outline" onClick={() => funnel.history.replace("게임타입입력")}>
                Cancel
              </Button>
            </DrawerClose>
          </DrawerFooter>
        </div>
      </DrawerContent>
    </Drawer>
  );
}
