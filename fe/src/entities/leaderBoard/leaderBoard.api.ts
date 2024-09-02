import { LeaderBoardScheme } from "./list.model";

export const useLeaderBoard = () => {

  const leaderBoard: LeaderBoardScheme[] = [
    {
      userId: "1",
      "rank": 1,
      username: "John",
      tier: "Gold",
      lp: 1000,
    },
    {
      userId: "2",
      rank: 2,
      username: "Jane",
      tier: "Silver",
      lp: 900,
    },
    {
      userId: "3",
      rank: 3,
      username: "Doe",
      tier: "Bronze",
      lp: 800,
    }
  ];

  const getLeaderBoard: () => Promise<LeaderBoardScheme[]> = async () => {
    return new Promise((resolve) => {
      setTimeout(() => {
        resolve(leaderBoard);
      }, 1000);
    });
  }

  return {
    getLeaderBoard
  };
}