import { GameType, MatchType } from "@/shared/api/game";
import {
  Button,
  Label,
  RadioGroup,
  RadioGroupItem,
  Select,
  SelectContent,
  SelectGroup,
  SelectItem,
  SelectLabel,
  SelectTrigger,
  SelectValue,
} from "@/shared/ui";
import { Separator } from "@radix-ui/react-select";
import { useState } from "react";

interface Props {
  onNext: (gameType: GameType, matchType: MatchType) => void;
}

export const GameTypeInput = ({ onPrev, onNext }: Props) => {
  const [gameType, setGameType] = useState<GameType>("normal");
  const [matchType, setMatchType] = useState<MatchType>("single");

  return (
    <div>
      <RadioGroup defaultValue={gameType} onValueChange={setGameType}>
        <Label className="text-lg">GameType</Label>
        {Object.values(GameType.Values).map((value) => (
          <div className="flex items-center space-x-2" key={value}>
            <RadioGroupItem value={value} id={`r-${value}`} />
            <Label htmlFor={`r-${value}`}>{value}</Label>
          </div>
        ))}
      </RadioGroup>

      <Separator className="my-8" />

      <Select value={matchType} onValueChange={setMatchType}>
        <Label className="text-lg">MatchType</Label>
        <SelectTrigger className="w-[180px]">
          <SelectValue placeholder="Select MatchType" />
        </SelectTrigger>
        <SelectContent>
          <SelectGroup>
            <SelectLabel>MatchType</SelectLabel>
            {Object.values(MatchType.Values).map((value) => (
              <SelectItem key={value} value={value}>
                {value}
              </SelectItem>
            ))}
          </SelectGroup>
        </SelectContent>
      </Select>

      <Separator className="my-8" />

      <Button onClick={() => onPrev()}>Prev</Button>
      <Button onClick={() => onNext(gameType, matchType)}>Next</Button>
    </div>
  );
};
