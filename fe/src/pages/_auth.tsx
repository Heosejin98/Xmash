import DepthHeader from "@/app/depthHeader.ui";
import { Card } from "@/shared/ui";
import { Outlet, createFileRoute, redirect } from "@tanstack/react-router";

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
      <DepthHeader>
        <h2 className="font-semibold text-lg">Profile</h2>
      </DepthHeader>
      <Card className="w-4/5 m-auto">
        <Outlet />
      </Card>
    </>
  );
}
