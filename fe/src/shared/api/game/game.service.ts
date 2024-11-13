import { AxiosContracts } from "@/shared/lib/axios";
import { api } from "..";
import { CreateGameDto, GameDto, GameParamsQueryDto, UpdateGameDto } from "./game.contracts";
import { z } from "zod";

export class GameService {
  static gameMutation(data: { createGameDto: CreateGameDto }) {
    const createGameDto = AxiosContracts.requestContract(CreateGameDto, data.createGameDto);
    return api.post(`/games`, { ...createGameDto });
  }

  static gameQuery(config: { params: GameParamsQueryDto; signal?: AbortSignal }) {
    return api.get(`/games`, config).then(AxiosContracts.responseContract(z.array(GameDto)));
  }

  static updateGameMutation(data: { gameId: string; updateGameDto: UpdateGameDto }) {
    const updateGameDto = AxiosContracts.requestContract(UpdateGameDto, data.updateGameDto);
    return api.patch(`/games/${data.gameId}`, { ...updateGameDto });
  }
}
