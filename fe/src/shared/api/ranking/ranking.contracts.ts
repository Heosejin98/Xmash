import { z } from "zod";

export const RankingDto = z.object({
  userId: z.string(),
  userName: z.string().nullish(),
  lp: z.number(),
  rank: z.number(),
  tier: z.string(),
})

export type RankingDto = z.infer<typeof RankingDto>;
