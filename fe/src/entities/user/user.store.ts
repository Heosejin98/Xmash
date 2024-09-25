
import { UserDto } from "@/shared/api/user";
import { create } from "zustand";


interface AuthStore {
  user: UserDto | null;
  isAuthenticated: boolean;
  setUser: (user: UserDto | null) => void;
  setAuthenticated: (isAuthenticated: boolean) => void;
}

export const useAuthStore = create<AuthStore>()((set) => ({
  user: null,
  isAuthenticated: false,
  setUser: (user) => set(state => ({ ...state, user })),
  setAuthenticated: (isAuthenticated) => set(state => ({ ...state, isAuthenticated })),
}));
