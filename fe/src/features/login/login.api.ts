import { FormSchema } from "@/entities/login";
import { Fetcher } from "@/shared/api/fetcher";
import { useAuthStore } from "@/entities/user/user.store";

export const useLogin = () => {
  const store = useAuthStore();

  const login = async (data: FormSchema) => {
    const result = await Fetcher.post('login', data);
    if (result.isSuccess) {
      store.login(true);
    }
    return result;
  }

  return {
    login
  };
}
