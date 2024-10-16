import { MatchType } from '@/shared/api/game';
import { RankingService } from '@/shared/api/ranking';
import {
  queryOptions
} from '@tanstack/react-query';

export class RankingQueries {
  static readonly keys = {
    root: ['ranking'] as const,
  }

  static rankingQuery(matchType: MatchType) {
    return queryOptions({
      queryKey: [...this.keys.root],
      queryFn: async ({ signal }) => {
        return (await RankingService.rankingQuery({ signal, matchType })).data;
      },
      initialData: [],
    })
  }
}
