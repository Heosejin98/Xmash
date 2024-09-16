import { GameService } from '@/shared/api/game';
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

  static gameQuery(type: 'normal' | 'rank') {
    return queryOptions({
      queryKey: [...this.keys.root],
      queryFn: async ({ signal }) => {
        return (await GameService.gameQuery({ params: { type }, signal })).data;
      },
    })
  }
}
