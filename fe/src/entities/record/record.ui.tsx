import { Input } from "@/shared/ui";
import { useEffect, useState } from "react";
import { RecordSchema } from "./record.model";
import { Avatar, AvatarFallback, AvatarImage } from "@/shared/ui";

export function RecordList() {
  const [searchValue, setSearchValue] = useState("");
  const [records, setRecords] = useState<RecordSchema[]>([]);

  useEffect(() => {
    new Promise((resolve) => {
      setTimeout(() => {
        resolve([
          {
            id: 1,
            playerA: {
              name: "Player A",
              avatar: "https://randomuser.me/api/portraits",
              score: 10,
            },
            playerB: {
              name: "Player B",
              avatar: "https://randomuser.me/api/portraits",
              score: 5,
            },
            date: "2021-01-01",
          },
        ]);
      }, 1000);
    }).then((data) => {
      setRecords(data);
    });
  }, []);

  return (
    <div className="w-full">
      <div className="flex items-center py-4">
        <Input
          placeholder="Filter names..."
          value={searchValue}
          onChange={(e) => setSearchValue(e.target.value)}
          className="max-w-sm"
        />
      </div>
      <div className="rounded-md border">
        {records.map((record) => (
          <div key={record.id} className="flex items-center border-b p-4 justify-between">
            <div>
              <Avatar className="mr-4">
                <AvatarImage src={record.playerA.avatar} alt={record.playerA.name} />
                <AvatarFallback>{record.playerA.name}</AvatarFallback>
              </Avatar>
            </div>

            <div className="flex gap-2">
              <div>{record.playerA.score}</div>:<div>{record.playerB.score}</div>
            </div>

            <div>
              <Avatar className="mr-4">
                <AvatarImage src={record.playerB.avatar} alt={record.playerB.name} />
                <AvatarFallback>{record.playerB.name}</AvatarFallback>
              </Avatar>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
