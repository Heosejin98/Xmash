import { Card } from "@/shared/ui";
import { createFileRoute, Link } from "@tanstack/react-router";
import { Gamepad2, Medal, Trophy, Plus } from "lucide-react";

export const Route = createFileRoute("/")({
  component: AuthLayout,
});

function AuthLayout() {
  return (
    <div className="flex items-center justify-center flex-col">
      <h1 className="text-4xl font-bold text-center text-gray-800 mt-8 mb-4">Xmash</h1>

      <div className="gap-2 grid grid-cols-2 grid-rows-2">
        <Card
          className="w-24 h-24 flex shadow-inner flex-col items-center justify-center
          text-gray-500 cursor-not-allowed bg-red-200"
        >
          <Trophy></Trophy>
          <span>TBU</span>
        </Card>
        <Link to="/add">
          <Card className="w-24 h-24 flex shadow-lg flex-col items-center justify-center shadow-blue-200 bg-blue-600 text-gray-100 ">
            <Plus className="animate-ping"></Plus>
            <span className="">경기 등록</span>
          </Card>
        </Link>
        <Link to="/ranking" search={{ matchType: "single" }}>
          <Card className="w-24 h-24 flex  shadow-lg flex-col items-center justify-center shadow-blue-200 bg-blue-600 text-gray-100 ">
            <Medal className="animate-pulse" />

            <span className="">랭킹 검색</span>
          </Card>
        </Link>
        <Link to="/game" search={{ gameType: "normal", matchType: "all" }}>
          <Card className="w-24 h-24 flex flex-col items-center justify-center shadow-yellow-100 bg-yellow-400 text-gray-800">
            <Gamepad2 className="animate-bounce" />

            <span className="">전적 검색</span>
          </Card>
        </Link>
      </div>
    </div>
  );
}
