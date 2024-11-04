import { z } from "zod";

export const MatchType = z.enum(["single", "double", "all"]);
export type MatchType = z.infer<typeof MatchType>;

export const PlayerDto = z.object({
  userId: z.string(),
  userName: z.string(),
  profileUrl: z.string().url().nullable(),
});
export type PlayerDto = z.infer<typeof PlayerDto>;

export const CreateGameDto = z.object({
  homeTeam: z.array(z.string()),
  awayTeam: z.array(z.string()),
  homeScore: z.coerce.number().int().step(1).nonnegative().gte(0).lte(50),
  awayScore: z.coerce.number().int().step(1).nonnegative().gte(0).lte(50),
  matchType: MatchType,
});
export type CreateGameDto = z.infer<typeof CreateGameDto>;

export const GameDto = z.object({
  winTeam: z.array(PlayerDto),
  loseTeam: z.array(PlayerDto),
  winnerScore: z.number(),
  loserScore: z.number(),
  matchTime: z.coerce.date().transform((dateString, ctx) => {
    const date = new Date(dateString);
    date.setHours(date.getHours() - (date.getTimezoneOffset() / 60));
    if (!z.date().safeParse(date).success) {
        ctx.addIssue({
            code: z.ZodIssueCode.invalid_date,
        })
    }
    return date;
}),
  matchType: z.string(),
  point: z.number().nullable(),
});
export type GameDto = z.infer<typeof GameDto>;

export const GameParamsQueryDto = z.object({
  matchType: MatchType,
});
export type GameParamsQueryDto = z.infer<typeof GameParamsQueryDto>;
