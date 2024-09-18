import { GameType } from "@/shared/api/game";
import { Label, RadioGroup, RadioGroupItem } from "@/shared/ui";
import { useState } from "react";

interface Props {
  onNext: (school: string) => void;
}

export const 게임타입입력 = ({ onNext }: Props) => {
  const [gameType, setGameType] = useState<GameType>("normal");

  return (
    <div>
      <RadioGroup defaultValue={gameType} onValueChange={setGameType}>
        {["normal", "rank"].map((value) => (
          <div className="flex items-center space-x-2">
            <RadioGroupItem value={value} id={`r-${value}`} />
            <Label htmlFor={`r-${value}`}>{value}</Label>
          </div>
        ))}
      </RadioGroup>
    </div>
  );
};
