import { UserDto } from "@/shared/api/auth";
import { Avatar, AvatarFallback, AvatarImage, Button } from "@/shared/ui";
import { useMemo, useState } from "react";

interface Props {
  onPrev: () => void;
  onNext: (winTeam: string[], loseTeam: string[]) => void;
}

export const PlayerInput = ({ onPrev, onNext }: Props) => {
  const [winUserList, setWinUserList] = useState<UserDto[]>([]);
  const [loseUserList, setLoseUserList] = useState<UserDto[]>([]);

  const winTeam = useMemo(() => winUserList.map((u) => u.userName), [winUserList]);
  const loseTeam = useMemo(() => loseUserList.map((u) => u.userName), [loseUserList]);

  return (
    <div>
      <div>
        {winUserList.map((user) => (
          <Avatar className="mr-4">
            <AvatarImage src={user.profileUrl ?? ""} alt={user.userName} />
            <AvatarFallback>{user.userName.slice(1, 3)}</AvatarFallback>
          </Avatar>
        ))}
        {winUserList.length < 2 && (
          <Button
            onClick={() => setWinUserList([...winUserList, { userName: "test", profileUrl: "" }])}
          >
            Add
          </Button>
        )}
      </div>
      <div>
        {loseUserList.map((user) => (
          <Avatar className="mr-4">
            <AvatarImage src={user.profileUrl ?? ""} alt={user.userName} />
            <AvatarFallback>{user.userName.slice(1, 3)}</AvatarFallback>
          </Avatar>
        ))}
        {loseUserList.length < 2 && (
          <Button
            onClick={() => setLoseUserList([...loseUserList, { userName: "test", profileUrl: "" }])}
          >
            Add
          </Button>
        )}
      </div>

      <Button onClick={() => onPrev()}>Prev</Button>
      <Button onClick={() => onNext(winTeam, loseTeam)}>Next</Button>
    </div>
  );
};
