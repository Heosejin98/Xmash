import { AxiosContracts } from "@/shared/lib/axios"
import { api } from ".."
import { LoginUserDto, UserDtoSchema } from "./auth.contracts"

export class AuthService {
  static loginUserMutation(data: { loginUserDto: LoginUserDto }) {
    const loginUserDto = AxiosContracts.requestContract(
      LoginUserDto,
      data.loginUserDto,
    )
    return api.post('/login', { ...loginUserDto }).then(AxiosContracts.responseContract(UserDtoSchema))
  }

  static currentUserQuery() {
    return api.get('/me').then(AxiosContracts.responseContract(UserDtoSchema))
  }
}
