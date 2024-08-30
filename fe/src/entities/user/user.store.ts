import { UserScheme } from "@/entities/user";
import { getCookie } from "@/shared/lib/getCookieByName";
import { useEffect } from "react";
import { create } from "zustand";


interface AuthStore {
  user: UserScheme | null;
  isAuthenticated: boolean;
  setUser: (user: UserScheme | null) => void;
  setAuthenticated: (isAuthenticated: boolean) => void;
}

const useStore = create<AuthStore>()((set) => ({
  user: null,
  isAuthenticated: false,
  setUser: (user) => set(state => ({ ...state, user })),
  setAuthenticated: (isAuthenticated) => set(state => ({ ...state, isAuthenticated })),
}));

export const useAuthStore = () => {
  const store = useStore();
  // const isAuthenticated = store.isAuthenticated;

  const login = (_login: boolean/**user: UserScheme*/) => {
    // store.setUser(user);
    store.setAuthenticated(true);
  }

  const logout = () => {
    // store.setUser(null);
    store.setAuthenticated(false);
  }

  useEffect(() => {
    // NOTE - This is a simple way to persist the user in local storage
    const session = getCookie('JSESSIONID');

    console.log('session', session, document.cookie)
    if (session) {
      // TODO get user from session
      store.setAuthenticated(true);
    }
  }, []);

  return {
    user: store.user,
    login,
    logout,
    isAuthenticated: store.isAuthenticated,
  };
}
