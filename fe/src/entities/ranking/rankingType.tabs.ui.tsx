import { Tabs, TabsList, TabsTrigger } from "@/shared/ui";
import { MatchType } from "@/shared/api/game";

interface RankingTypeTabsProps {
  onChange: (type: MatchType) => void;
  type: MatchType;
}

export const RankingTypeTabs = ({ type, onChange }: RankingTypeTabsProps) => {
  return (
    <Tabs value={type} className="w-full" onValueChange={(type) => onChange(type as MatchType)}>
      <TabsList className="grid grid-cols-2">
        <TabsTrigger value="single">Single</TabsTrigger>
        <TabsTrigger value="double">Double</TabsTrigger>
      </TabsList>
    </Tabs>
  );
};
