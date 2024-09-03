import { z } from "zod";

export const LeaderBoardSchema = z.object({
  userId: z.string(),
  rank: z.number(),
  username: z.string(),
  tier: z.string(),
  lp: z.number(),
  // email: z.string().email(),
  // profilePicture: z.string(),
})

export type LeaderBoardScheme = z.infer<typeof LeaderBoardSchema>;