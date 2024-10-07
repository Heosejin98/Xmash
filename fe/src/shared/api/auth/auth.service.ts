  import { AxiosContracts } from "@/shared/lib/axios"
import { api } from ".."
import { LoginUserDto, LogoutDto, PasswordResetDto } from "./auth.contracts"
import { UserDto } from "@/shared/api/user"

export class AuthService {
  static loginUserMutation(data: { loginUserDto: LoginUserDto }) {
    const loginUserDto = AxiosContracts.requestContract(
      LoginUserDto,
      data.loginUserDto,
    )
    return api.post('/login', { ...loginUserDto }).then(AxiosContracts.responseContract(UserDto))
  }

  static currentUserQuery(config: { signal?: AbortSignal }) {
    return api.get('/me', config).then(AxiosContracts.responseContract(UserDto))
  }

  static logoutMutation() {
    return api.post('/logout').then(AxiosContracts.responseContract(LogoutDto))
  }

  static passwordResetMutation(data: { passwordResetDto: PasswordResetDto }) {
    const passwordResetDto = AxiosContracts.requestContract(
      PasswordResetDto,
      data.passwordResetDto,
    )
    return api.patch('/me/password', { ...passwordResetDto }).then(AxiosContracts.responseContract(UserDto))
  }
}
