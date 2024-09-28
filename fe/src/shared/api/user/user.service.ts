import { AxiosContracts } from "@/shared/lib/axios";
import { api } from "..";
import { z } from "zod";
import { UserDto } from "./user.contracts";

export class UserService {
  static usersQuery(config: { signal?: AbortSignal }) {
    return api.get('/users', config).then(AxiosContracts.responseContract(z.array(UserDto)))
  }

  // static usersQueryById({userId, ...config}: { signal?: AbortSignal, userId: string }) {
  //   return api.get(`/user/${userId}`, config).then(AxiosContracts.responseContract(z.array(UserDto)))
  // }

  static meQuery(config: { signal?: AbortSignal }) {
    return api.get('/me', config).then(AxiosContracts.responseContract(UserDto))
  }
}
