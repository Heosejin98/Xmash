import { UserScheme } from "@/entities/user";
import { useEffect } from "react";
import { create } from "zustand";


interface AuthStore {
  user: UserScheme | null;
  setUser: (user: UserScheme | null) => void;
}

const useStore = create<AuthStore>()((set) => ({
  user: null,
  setUser: (user) => set(state => ({ ...state, user })),
}));

export const useAuthStore = () => {
  const store = useStore();
  const isAuthenticated = false;// = useMemo(() => store.user !== null, [store.user]);

  const login = (user: UserScheme) => {
    store.setUser(user);
  }

  const logout = () => {
    store.setUser(null);
  }

  useEffect(() => {
    // NOTE - This is a simple way to persist the user in local storage
    const session = localStorage.getItem("user");
    if (session) {
      // TODO get user from session
    }
  }, []);

  return {
    user: store.user,
    login,
    logout,
    isAuthenticated,
  };
}
