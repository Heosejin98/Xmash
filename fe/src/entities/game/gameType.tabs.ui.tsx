import { GameType } from "@/shared/api/game";
import { Tabs, TabsList, TabsTrigger } from "@/shared/ui";

interface GameTypeTabsProps {
  onChange: (type: GameType) => void;
  type: GameType;
}

export const GameTypeTabs = ({ type, onChange }: GameTypeTabsProps) => {
  return (
    <Tabs
      defaultValue={type}
      className="w-full"
      onValueChange={(type) => onChange(type as GameType)}
    >
      <TabsList className="grid grid-cols-2">
        <TabsTrigger value="normal">Normal</TabsTrigger>
        <TabsTrigger value="rank">Rank</TabsTrigger>
      </TabsList>
    </Tabs>
  );
};
