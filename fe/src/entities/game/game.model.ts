import { z } from "zod";

export const PlayerSchema = z.object({
  userId: z.string(),
  userName: z.string(),
  profileUrl: z.string().url().nullable(),
});

export const GameSchema = z.object({
  winTeam: z.array(PlayerSchema),
  loseTeam: z.array(PlayerSchema),
  winnerScore: z.number(),
  loserScore: z.number(),
  matchTime: z.string(),
  matchType: z.string(),
  point: z.number().nullable(),
});

export type PlayerSchema = z.infer<typeof PlayerSchema>;
export type GameSchema = z.infer<typeof GameSchema>;
