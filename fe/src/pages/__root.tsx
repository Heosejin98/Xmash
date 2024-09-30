import { useAuthStore } from "@/entities/user/user.store";
import { UserService } from "@/shared/api/user";
import { createRootRouteWithContext, Outlet } from "@tanstack/react-router";
import { useEffect } from "react";
import { Toaster } from "@/shared/ui";

export interface MyRouterContext {
  // The ReturnType of your useAuth hook or the value of your AuthContext
  isAuthenticated: boolean;
}

export const Route = createRootRouteWithContext<MyRouterContext>()({
  component: () => {
    const store = useAuthStore();
    useEffect(() => {
      UserService.meQuery({})
        .then((res) => {
          store.setUser(res.data);
          store.setAuthenticated(true);
        })
        .catch(() => {
          console.log("not authenticated");
        });
    }, []);
    return (
      <>
        <Outlet />
        <Toaster position="top-center" toastOptions={{}} />
        {/* <TanStackRouterDevtools /> */}
      </>
    );
  },
});
