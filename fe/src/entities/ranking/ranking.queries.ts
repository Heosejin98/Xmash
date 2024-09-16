import { RankingService } from '@/shared/api/ranking';
import {
  queryOptions
} from '@tanstack/react-query';

export class RankingQueries {
  static readonly keys = {
    root: ['ranking'] as const,
  }

  static rankingQuery() {
    return queryOptions({
      queryKey: [...this.keys.root],
      queryFn: async ({ signal }) => {
        return (await RankingService.rankingQuery({ signal })).data;
      },
      initialData: [],
    })
  }
}
