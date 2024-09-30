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
        <TabsTrigger value="all">ALL</TabsTrigger>
        <TabsTrigger value="single">싱글</TabsTrigger>
        <TabsTrigger value="double">더블</TabsTrigger>
      </TabsList>
    </Tabs>
  );
};
