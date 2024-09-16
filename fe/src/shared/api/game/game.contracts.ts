import { z } from "zod";

export const PlayerDto = z.object({
  userId: z.string(),
  userName: z.string(),
  profileUrl: z.string().url().nullable(),
});
export type PlayerDto = z.infer<typeof PlayerDto>;

export const CreateGameDto = z.object({
  winTeam: z.array(PlayerDto),
  loseTeam: z.array(PlayerDto),
  winnerScore: z.number(),
  loserScore: z.number(),
  matchTime: z.string(),
  matchType: z.string(),
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
  type: z.enum(["normal", "rank"]),
});
export type GameParamsQueryDto = z.infer<typeof GameParamsQueryDto>;
