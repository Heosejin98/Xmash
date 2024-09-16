import NavBar from "@/app/navBar.ui";
import { createRootRouteWithContext, Outlet } from "@tanstack/react-router";

export interface MyRouterContext {
  // The ReturnType of your useAuth hook or the value of your AuthContext
  isAuthenticated: boolean;
}

export const Route = createRootRouteWithContext<MyRouterContext>()({
  component: () => {
    return (
      <>
        <Outlet />

        <NavBar></NavBar>

        {/* <TanStackRouterDevtools /> */}
      </>
    );
  },
});
