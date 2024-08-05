import { z } from "zod";

export const FormSchema = z.object({
  userId: z
    .string()
    .min(2, {
      message: "UserId must be at least 2 characters.",
    })
    .email({
      message: "UserId must be a valid email address.",
    }),
  password: z.string().min(8, {
    message: "Password must be at least 8 characters.",
  }),
});

export type FormSchema = z.infer<typeof FormSchema>;
