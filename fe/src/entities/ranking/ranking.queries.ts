import { api } from '@/shared/api'
import { AxiosContracts } from '@/shared/lib/axios';
import {
  queryOptions
} from '@tanstack/react-query'
import { RankingSchema } from './ranking.model';
import { z } from 'zod';

export class RankingQueries {
  static readonly keys = {
    root: ['ranking'] as const,
  }

  static rankingQuery() {
    return queryOptions({
      queryKey: [...this.keys.root],
      queryFn: async ({ signal }) => {
        const response = await api.get('/ranking', { signal }).then(AxiosContracts.responseContract(z.array(RankingSchema)));
        return response.data;
      },
    })
  }
}
