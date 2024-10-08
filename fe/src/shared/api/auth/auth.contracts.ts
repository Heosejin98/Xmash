import { z } from "zod";

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


export const PasswordResetDto = z.object({
  prevPassword: z.string(),
  newPassword: z.string(),
  confirmNewPassword: z.string(),
})
export type PasswordResetDto = z.infer<typeof PasswordResetDto>

export const LogoutDto = z.boolean();
export type LogoutDto = z.infer<typeof LogoutDto>
