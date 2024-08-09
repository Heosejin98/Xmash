import { FormSchema } from "@/entities/login";
import { Fetcher } from "@/shared/api/fetcher";
import { useAuthStore } from "../user/useAuthStore";

export const useLogin = () => {
  const store = useAuthStore();
  const login = async (data: FormSchema) => {
    const result = await Fetcher.post('http://localhost:3000/ogin', data);
    store.login(result.data);
    return result.data;
  }

  return {
    login
  };
}
