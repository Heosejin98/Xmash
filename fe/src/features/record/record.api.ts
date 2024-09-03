import { Fetcher } from "@/shared/api/fetcher";
import { NewRecordSchema, UpdateRecordSchema } from "./record.model";

export const useRecord = () => {
  const createRecord = async (data: NewRecordSchema) => {
    const req = NewRecordSchema.safeParse(data);

    if (!req.success) {
      throw new Error("Invalid data");
    }

    const result = await Fetcher.post("record", {
      body: JSON.stringify(data),
    });
    return result;
  };

  const updateRecord = async (data: UpdateRecordSchema) => {
    const req = UpdateRecordSchema.safeParse(data);

    if (!req.success) {
      throw new Error("Invalid data");
    }

    const result = await Fetcher.put(`record/${data.id}`, {
      body: JSON.stringify(data),
    });
    return result;
  };

  return {
    createRecord,
    updateRecord,
  };
};
