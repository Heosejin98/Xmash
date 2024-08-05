import { z } from "zod";

export const UserSchema = z.object({
  id: z.string(),
  username: z.string(),
  rating: z.number(),
  email: z.string().email(),
})

export type UserScheme = z.infer<typeof UserSchema>;
