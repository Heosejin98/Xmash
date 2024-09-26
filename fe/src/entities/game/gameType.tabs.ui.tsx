import { GameType } from "@/shared/api/game";
import { Tabs, TabsList, TabsTrigger } from "@/shared/ui";
import { useState } from "react";

export const GameTypeTabs = () => {
  const [gameType, setGameType] = useState<GameType>("normal");

  return (
    <Tabs
      defaultValue="normal"
      className="w-full"
      onValueChange={(type) => setGameType(type as GameType)}
    >
      <TabsList className="grid grid-cols-2">
        <TabsTrigger value="normal">Normal</TabsTrigger>
        <TabsTrigger value="rank">Rank</TabsTrigger>
      </TabsList>
    </Tabs>
  );
};
