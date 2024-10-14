import { z } from "zod";

export const LoginUserDto = z.object({
  userId: z.string(),
  password: z.string(),
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
