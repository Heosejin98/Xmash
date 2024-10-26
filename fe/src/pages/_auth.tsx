import { Outlet, createFileRoute, redirect } from "@tanstack/react-router";
import DepthHeader from "@/app/depthHeader.ui";

export const Route = createFileRoute("/_auth")({
  beforeLoad: ({ context, location }) => {
    if (!context.isAuthenticated) {
      throw redirect({
        to: "/login",
        replace: true,
        search: {
          // Use the current location to power a redirect after login
          // (Do not use `router.state.resolvedLocation` as it can
          // potentially lag behind the actual current location)
          redirect: location.href,
        },
      });
    }
  },
  component: AuthLayout,
});

function AuthLayout() {
  return (
    <>
      <DepthHeader></DepthHeader>
      <Outlet />
    </>
  );
}
