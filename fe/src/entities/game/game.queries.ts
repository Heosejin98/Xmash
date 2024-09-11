import {
  queryOptions
} from '@tanstack/react-query';
import { GameSchema } from './game.model';
import { AxiosContracts } from '@/shared/lib/axios';
import { api } from '@/shared/api';
import { z } from 'zod';

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
        const response = await api.get(`/game/${type}`, { signal }).then(AxiosContracts.responseContract(z.array(GameSchema)));
        return (response.data)
      },
    })
  }
}
