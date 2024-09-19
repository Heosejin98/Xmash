import { z } from "zod";

export const UserDto = z.object({
  userId: z.string(),
  userName: z.string(),
  profileUrl: z.string(),
})
export type UserDto = z.infer<typeof UserDto>

export const LoginUserDto = z.object({
  userId: z
    .string()
    .min(2, {
      message: "UserId must be at least 2 characters.",
    }),
  password: z.string().min(4, {
    message: "Password must be at least 4 characters.",
  }),
})
export type LoginUserDto = z.infer<typeof LoginUserDto>
