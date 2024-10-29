import { CreateGameDto, MatchType } from "@/shared/api/game";

export type MatchTypeInput = Partial<CreateGameDto>;

export type HomeTeamInput = Partial<CreateGameDto> & {
  matchType: MatchType;
}

export type AwayTeamInput = Partial<CreateGameDto> & {
  matchType: MatchType;
  homeTeam: string[];
}

export type ScoresInput = Partial<CreateGameDto> & {
  matchType: MatchType;
  homeTeam: string[];
  awayTeam: string[]
};
