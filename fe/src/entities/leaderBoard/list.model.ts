import { z } from "zod";

export const LeaderBoardSchema = z.object({
  id: z.string(),
  ranking: z.number(),
  username: z.string(),
  tier: z.string(),
  rating: z.number(),
  // email: z.string().email(),
  // profilePicture: z.string(),
})

export type LeaderBoardScheme = z.infer<typeof LeaderBoardSchema>;