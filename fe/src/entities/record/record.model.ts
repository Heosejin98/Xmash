import { z } from "zod";

export const PlayerSchema = z.object({
  userId: z.string(),
  name: z.string(),
  avatar: z.string(),
  score: z.number(),
});

export const RecordSchema = z.object({
  id: z.string(),
  playerA: PlayerSchema,
  playerB: PlayerSchema,
  date: z.string(),
});

export type PlayerSchema = z.infer<typeof PlayerSchema>;
export type RecordSchema = z.infer<typeof RecordSchema>;
