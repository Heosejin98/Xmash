import { UserService } from '@/shared/api/user';
import {
  queryOptions
} from '@tanstack/react-query';

export class UserQueries {
  static readonly keys = {
    root: ['user'] as const,
  }

  static userQuery() {
    return queryOptions({
      queryKey: [...this.keys.root],
      queryFn: async ({ signal }) => {
        return (await UserService.usersQuery({ signal })).data;
      },
      staleTime: 1000 * 60 * 5,
    })
  }
}
