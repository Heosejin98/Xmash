import { FormSchema } from "@/entities/login";
import { useAuthStore } from "@/entities/user/user.store";
import { api } from "@/shared/api";

export const useLogin = () => {
  const store = useAuthStore();

  const login = async (data: FormSchema) => {
    const { status } = await api.post('login', { body: data });
    if (status === 200) {
      store.login(true);
    }

  }

  return {
    login
  };
}
