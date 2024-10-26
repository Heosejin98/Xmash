import { CreateGameDto } from "@/shared/api/game";

// 1. Nothing entered
export type HomePlayerInput = Partial<CreateGameDto>;
// 2. home Player entered
export type AwayPlayerInput = Partial<CreateGameDto> & { homeTeam: string[] };
// 3. score entered
export type HomeScoreInput = Partial<CreateGameDto> & { homeTeam: string[]; awayTeam: string[] };
// 4. score entered
export type AwayScoreInput = Partial<CreateGameDto> & {
  homeTeam: string[];
  awayTeam: string[];
  homeScore: number;
};
// 5. GameType entered
export type GameTypeInput = Partial<CreateGameDto> & {
  homeTeam: string[];
  awayTeam: string[];
  homeScore: number;
  awayScore: number;
};
