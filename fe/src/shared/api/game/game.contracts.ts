import { z } from "zod";



export const MatchType = z.enum(["mixed-single", "male-single", "female-single", "mixed-double", "male-double", "female-double", "single", "double"]);
export type MatchType = z.infer<typeof MatchType>;

export const GameType = z.enum(["normal", "rank"]);
export type GameType = z.infer<typeof GameType>;


export const PlayerDto = z.object({
  userId: z.string(),
  userName: z.string(),
  profileUrl: z.string().url().nullable(),
});
export type PlayerDto = z.infer<typeof PlayerDto>;

export const CreateGameDto = z.object({
  winTeam: z.array(z.string()).nonempty(),
  loseTeam: z.array(z.string()).nonempty(),
  homeScore: z.number(),
  awayScore: z.number(),
  matchType: MatchType,
  gameType: GameType,
  point: z.number().nullable(),
});
export type CreateGameDto = z.infer<typeof CreateGameDto>;

export const GameDto = z.object({
  id: z.string(),
  winTeam: z.array(PlayerDto),
  loseTeam: z.array(PlayerDto),
  winnerScore: z.number(),
  loserScore: z.number(),
  matchTime: z.string(),
  matchType: z.string(),
  point: z.number().nullable(),
});
export type GameDto = z.infer<typeof GameDto>;

export const GameParamsQueryDto = z.object({
  matchType: MatchType,
  gameType: GameType,
});
export type GameParamsQueryDto = z.infer<typeof GameParamsQueryDto>;
