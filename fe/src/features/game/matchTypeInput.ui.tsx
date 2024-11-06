import { MatchType } from "@/shared/api/game";
import { Button, Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/shared/ui";
import { Sword, Swords } from "lucide-react";
import DepthHeader from "@/app/depthHeader.ui";

interface Props {
  onNext: (matchType: MatchType) => void;
  onPrev?: () => void;
}

export const MatchTypeInputButton = ({ onNext, onPrev }: Props) => {
  return (
    <>
      <DepthHeader onPrev={onPrev}></DepthHeader>

      <Card className="w-[350px] m-auto">
        <CardHeader>
          <CardTitle>경기 타입</CardTitle>
          <CardDescription>경기 타입을 선택해 주세요</CardDescription>
        </CardHeader>
        <CardContent className="flex justify-between">
          <Button className="w-28 h-28 flex flex-col gap-2" onClick={() => onNext("single")}>
            <Sword size="40px"></Sword>
            <span>단식</span>
          </Button>
          <Button className="w-28 h-28 flex flex-col gap-2" onClick={() => onNext("double")}>
            <Swords size="40px"></Swords>
            <span>복식</span>
          </Button>
        </CardContent>
      </Card>
    </>
  );
};
