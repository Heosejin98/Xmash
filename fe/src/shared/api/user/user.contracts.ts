import { z } from 'zod'

export const UserDto = z.object({
  userId: z.string(),
  userName: z.string(),
  profileUrl: z.string(),
})
export type UserDto = z.infer<typeof UserDto>
