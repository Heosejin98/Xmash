import { GameParamsQueryDto, GameService } from '@/shared/api/game';
import {
  queryOptions
} from '@tanstack/react-query';

export class GameQueries {
  static readonly keys = {
    root: ['game'] as const,
    rootBySlug: ['game', 'by-slug'] as const,
    rootInfinity: ['game', 'infinite-games'] as const,
    generalInfinity: [
      'game',
      'infinite-games',
      'general-games',
    ] as const,
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
