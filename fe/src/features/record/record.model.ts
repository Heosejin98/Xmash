import { z } from "zod";

export const NewRecordSchema = z.object({
  userA: z.string(),
  userB: z.string(),
  scoreA: z.number(),
  scoreB: z.number(),
  date: z.string().optional(),
});

export const UpdateRecordSchema = z.object({
  id: z.string(),
  userA: z.string().optional(),
  userB: z.string().optional(),
  scoreA: z.number().optional(),
  scoreB: z.number().optional(),
  date: z.string().optional(),
});

export type NewRecordSchema = z.infer<typeof NewRecordSchema>;
export type UpdateRecordSchema = z.infer<typeof UpdateRecordSchema>;