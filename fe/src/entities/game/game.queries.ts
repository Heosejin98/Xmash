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

  static gameQuery({ matchType }: GameParamsQueryDto) {
    return queryOptions({
      queryKey: [...this.keys.root, matchType],
      queryFn: async ({ signal }) => {
        return (await GameService.gameQuery({
          params: {
            matchType,
          }, signal
        })).data;
      },
    })
  }
}
