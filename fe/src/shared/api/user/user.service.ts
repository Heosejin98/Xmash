import { AxiosContracts } from "@/shared/lib/axios";
import { api } from "..";
import { z } from "zod";
import { UserDto } from "./user.contracts";

export class UserService {
  static usersQuery(config: { signal?: AbortSignal }) {
    return api.get('/user/all', config).then(AxiosContracts.responseContract(z.array(UserDto)))
  }

  // static usersQueryById({userId, ...config}: { signal?: AbortSignal, userId: string }) {
  //   return api.get(`/user/${userId}`, config).then(AxiosContracts.responseContract(z.array(UserDto)))
  // }
}
