import { GameType } from "@/shared/api/game";
import { Tabs, TabsList, TabsTrigger } from "@/shared/ui";

interface GameTypeTabsProps {
  onChange: (type: GameType) => void;
  type: GameType;
}

export const GameTypeTabs = ({ type, onChange }: GameTypeTabsProps) => {
  return (
    <Tabs value={type} className="w-full" onValueChange={(type) => onChange(type as GameType)}>
      <TabsList className="grid grid-cols-2">
        <TabsTrigger value="normal">노말</TabsTrigger>
        <TabsTrigger value="rank">랭크</TabsTrigger>
      </TabsList>
    </Tabs>
  );
};
