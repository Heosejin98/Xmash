import { MatchType } from "@/shared/api/game";
import { Tabs, TabsList, TabsTrigger } from "@/shared/ui";
import { useState } from "react";

export const MatchTypeTabs = () => {
  const [_, setMatchType] = useState<MatchType>("single");

  return (
    <Tabs
      defaultValue="normal"
      className="w-full"
      onValueChange={(type) => setMatchType(type as MatchType)}
    >
      <TabsList className="grid grid-cols-3">
        <TabsTrigger value="all">ALL</TabsTrigger>
        <TabsTrigger value="single">싱글</TabsTrigger>
        <TabsTrigger value="double">더블</TabsTrigger>
      </TabsList>
    </Tabs>
  );
};
