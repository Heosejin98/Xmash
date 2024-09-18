import { CreateGameDto, GameType, MatchType } from "@/shared/api/game";

type Context = Partial<CreateGameDto>;

export type 게임타입입력 = Context & {
  gameType?: GameType;
  matchType?: MatchType;
}

export type 플레이어입력 = Context & {
  gameType: GameType;
  matchType: MatchType;
  winTeam?: string[];
  loseTeam?: string[];
}

export type 점수입력 = Context & {
  gameType: GameType;
  matchType: MatchType;
  winTeam: string[];
  loseTeam: string[];
  homeScore?: number;
  awayScore?: number;
}


