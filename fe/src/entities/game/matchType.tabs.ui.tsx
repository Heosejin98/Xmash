import { MatchType } from "@/shared/api/game";
import { Tabs, TabsList, TabsTrigger } from "@/shared/ui";

interface MatchTypeTabsProps {
  onChange: (type: MatchType) => void;
  type: MatchType;
}

export const MatchTypeTabs = ({ type, onChange }: MatchTypeTabsProps) => {
  return (
    <Tabs value={type} className="w-full" onValueChange={(type) => onChange(type as MatchType)}>
      <TabsList className="grid grid-cols-3">
        <TabsTrigger value="all">전체</TabsTrigger>
        <TabsTrigger value="single">단식</TabsTrigger>
        <TabsTrigger value="double">복식</TabsTrigger>
      </TabsList>
    </Tabs>
  );
};
