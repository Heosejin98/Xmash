
import { api } from "@/shared/api";
import { NewRecordSchema } from "./record.model";

export const useRecord = () => {
  const createRecord = async (data: NewRecordSchema) => {
    const req = NewRecordSchema.safeParse(data);

    if (!req.success) {
      throw new Error("Invalid data");
    }

    const result = await api.post("record", {
      query: {
        gameType: data.gameType,
      },
      body: {
        ...data
      },
    });
    return result;
  };

  return {
    createRecord,
  }
};
