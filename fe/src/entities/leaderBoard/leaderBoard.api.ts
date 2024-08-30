import { LeaderBoardScheme } from "./list.model";

export const useLeaderBoard = () => {

  const leaderBoard: LeaderBoardScheme[] = [
    {
      id: "1",
      "ranking": 1,
      username: "John",
      tier: "Gold",
      rating: 1000,
    },
    {
      id: "2",
      ranking: 2,
      username: "Jane",
      tier: "Silver",
      rating: 900,
    },
    {
      id: "3",
      ranking: 3,
      username: "Doe",
      tier: "Bronze",
      rating: 800,
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