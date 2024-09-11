import { z } from "zod";

export const RankingSchema = z.object({
  userId: z.string(),
  rank: z.number(),
  username: z.string().nullish(),
  tier: z.string(),
  lp: z.number(),
})

export type RankingSchema = z.infer<typeof RankingSchema>;
