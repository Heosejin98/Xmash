import { z } from "zod";


export const GameType = z.enum(["normal", "rank"]);

export const NewRecordSchema = z.object({
  gameType: GameType,
  homeScore: z.number(),
  awayScore: z.number(),
  homePlayerIds: z.array(z.string()),
  awayPlayerIds: z.array(z.string()),
});

// export const UpdateRecordSchema = z.object({
//   id: z.string(),
//   userA: z.string().optional(),
//   userB: z.string().optional(),
//   scoreA: z.number().optional(),
//   scoreB: z.number().optional(),
//   date: z.string().optional(),
// });

export type NewRecordSchema = z.infer<typeof NewRecordSchema>;
// export type UpdateRecordSchema = z.infer<typeof UpdateRecordSchema>;
