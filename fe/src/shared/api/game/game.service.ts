import { AxiosContracts } from "@/shared/lib/axios"
import { api } from ".."
import { CreateGameDto, GameDto, GameParamsQueryDto } from "./game.contracts"
import { z } from "zod"


export class GameService {
  static gameMutation(data: { createGameDto: CreateGameDto }) {
    const createGameDto = AxiosContracts.requestContract(
      CreateGameDto,
      data.createGameDto,
    )
    return api.post(`/game/${data.createGameDto.gameType}/${data.createGameDto.matchType}`, { ...createGameDto })
  }

  static gameQuery(config: { params: GameParamsQueryDto, signal?: AbortSignal }) {
    return api.get(`/game/${config.params.gameType}/${config.params.matchType}`, config).then(AxiosContracts.responseContract(z.array(GameDto)))
  }
}
