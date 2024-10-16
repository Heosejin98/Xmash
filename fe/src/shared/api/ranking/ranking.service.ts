import { AxiosContracts } from "@/shared/lib/axios";
import { api } from "..";
import { RankingDto } from "./ranking.contracts";
import { z } from "zod";
import { MatchType } from "../game";

export class RankingService {
  static rankingQuery(config: { matchType: MatchType; signal?: AbortSignal }) {
    return api.get(`/ranking/${config.matchType}`, config).then(AxiosContracts.responseContract(z.array(RankingDto)))
  }
}
