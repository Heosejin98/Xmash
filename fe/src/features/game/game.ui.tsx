import {
  Drawer,
  DrawerTrigger,
  DrawerClose,
  DrawerContent,
  DrawerHeader,
  DrawerFooter,
  DrawerTitle,
  DrawerDescription,
  Button,
  Switch,
  Label,
} from "@/shared/ui";
import { Plus } from "lucide-react";
import { useFunnel } from "@use-funnel/browser";
import type { 게임타입입력, 점수입력, 플레이어입력 } from "./game.context";
import { 게임타입입력 } from "./gameType.ui";

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
            <DrawerTitle>경기 등록 </DrawerTitle>
            <DrawerDescription>선수와 점수를 설정하세요.</DrawerDescription>
          </DrawerHeader>
          <div className="p-4 pb-0">
            <funnel.Render
              게임타입입력={({ history }) => (
                <게임타입입력
                  onNext={(gameType, matchType) =>
                    history.push("플레이어입력", { gameType, matchType })
                  }
                />
              )}
              플레이어입력={({ history }) => (
                <div
                  onNext={(winTeam, loseTeam) => history.push("점수입력", { winTeam, loseTeam })}
                />
              )}
              점수입력={({ context }) => <div {...context} />}
            ></funnel.Render>
          </div>
          <DrawerFooter>
            <Button>Submit </Button>
            <DrawerClose asChild>
              <Button variant="outline"> Cancel </Button>
            </DrawerClose>
          </DrawerFooter>
        </div>
      </DrawerContent>
    </Drawer>
  );
}
