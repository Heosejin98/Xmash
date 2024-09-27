import { z } from 'zod'

export const UserDto = z.object({
  userId: z.string(),
  userName: z.string(),
  userEmail: z.string().optional(),
  gender: z.enum(['남', '여']).optional(),
  profileUrl: z.string().optional(),
})
export type UserDto = z.infer<typeof UserDto>
