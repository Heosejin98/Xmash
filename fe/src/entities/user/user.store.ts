import { UserDtoSchema } from "@/shared/api/auth";
import { create } from "zustand";


interface AuthStore {
  user: UserDtoSchema | null;
  isAuthenticated: boolean;
  setUser: (user: UserDtoSchema | null) => void;
  setAuthenticated: (isAuthenticated: boolean) => void;
}

export const useAuthStore = create<AuthStore>()((set) => ({
  user: null,
  isAuthenticated: false,
  setUser: (user) => set(state => ({ ...state, user })),
  setAuthenticated: (isAuthenticated) => set(state => ({ ...state, isAuthenticated })),
}));
