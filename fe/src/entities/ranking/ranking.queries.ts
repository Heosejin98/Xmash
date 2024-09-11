import { api } from '@/shared/api'
import { AxiosContracts } from '@/shared/lib/axios';
import {
  queryOptions
} from '@tanstack/react-query'
import { RankingSchema } from './ranking.model';

export class RankingQueries {
  static readonly keys = {
    root: ['ranking'] as const,
  }

  static rankingQuery() {
    return queryOptions({
      queryKey: [...this.keys.root],
      queryFn: async ({ signal }) => {
        const response = await api.get('/ranking', { signal }).then(AxiosContracts.responseContract(RankingSchema.array()));
        console.log(response.data)
        return response.data;
      },
    })
  }
}
