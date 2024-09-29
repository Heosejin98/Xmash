import { z } from "zod";



export const MatchType = z.enum(["mixed-single", "male-single", "female-single", "mixed-double", "male-double", "female-double", "single", "double", "all"]);
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
  homeTeam: z.array(z.string()).nonempty(),
  awayTeam: z.array(z.string()).nonempty(),
  homeScore: z.coerce.number().int().step(1).nonnegative().gte(0).lte(50),
  awayScore: z.coerce.number().int().step(1).nonnegative().gte(0).lte(50),
  matchType: MatchType,
  gameType: GameType,
});
export type CreateGameDto = z.infer<typeof CreateGameDto>;

export const GameDto = z.object({
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
