import { z } from "zod";

export const RankingSchema = z.object({
  userId: z.string(),
  userName: z.string().nullish(),
  lp: z.number(),
  rank: z.number(),
  tier: z.string(),
})

export type RankingSchema = z.infer<typeof RankingSchema>;
