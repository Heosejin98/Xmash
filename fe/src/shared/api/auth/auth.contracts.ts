import { z } from "zod";

export const LoginUserDto = z.object({
  userId: z.string(),
  password: z.string(),
})
export type LoginUserDto = z.infer<typeof LoginUserDto>


export const PasswordResetDto = z.object({
  prevPassword: z.string().min(1, { message: "현재 비밀번호를 입력해주세요." }).default(""),
  newPassword: z.string().min(1, { message: "새 비밀번호를 입력해주세요." }).default(""),
  confirmNewPassword: z.string().min(1, { message: "새 비밀번호를 한번 더 입력해주세요." }).default(""),
})
export type PasswordResetDto = z.infer<typeof PasswordResetDto>

export const LogoutDto = z.boolean();
export type LogoutDto = z.infer<typeof LogoutDto>
