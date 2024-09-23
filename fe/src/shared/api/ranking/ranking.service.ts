import { AxiosContracts } from "@/shared/lib/axios";
import { api } from "..";
import { RankingDto } from "./ranking.contracts";
import { z } from "zod";

export class RankingService {
  static rankingQuery(config: { signal?: AbortSignal }) {
    return api.get('/ranking', config).then(AxiosContracts.responseContract(z.array(RankingDto)))
  }
}
