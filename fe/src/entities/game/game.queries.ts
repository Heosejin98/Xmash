import { GameParamsQueryDto, GameService } from '@/shared/api/game';
import {
  queryOptions
} from '@tanstack/react-query';

export class GameQueries {
  static readonly keys = {
    root: ['game'] as const,
    normal: ['game','normal'] as const,
    rank: ['game', 'rank'] as const,
  }

  static gameQuery({ gameType, matchType }: GameParamsQueryDto) {
    return queryOptions({
      queryKey: [...this.keys.root, gameType, matchType],
      queryFn: async ({ signal }) => {
        return (await GameService.gameQuery({
          params: {
            gameType,
            matchType,
          }, signal
        })).data;
      },
    })
  }
}
