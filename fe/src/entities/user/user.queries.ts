import { UserService } from '@/shared/api/user';
import {
  queryOptions
} from '@tanstack/react-query';

export class UserQueries {
  static readonly keys = {
    root: ['user'] as const,
  }

  static userAllQuery() {
    return queryOptions({
      queryKey: [...this.keys.root, 'all'],
      queryFn: async ({ signal }) => {
        return (await UserService.usersQuery({ signal })).data;
      },
      staleTime: 1000 * 60 * 5,
    })
  }

  static meQuery() {
    return queryOptions({
      queryKey: [...this.keys.root, 'me'],
      queryFn: async ({ signal }) => {
        return (await UserService.meQuery({ signal })).data;
      },
      staleTime: 1000 * 60 * 5,
    })
  }
}
