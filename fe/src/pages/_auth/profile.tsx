import { useLogout } from "@/features/login/logout.hook";
import { Button, CardContent, CardDescription, CardHeader, CardTitle } from "@/shared/ui";
import { createFileRoute } from "@tanstack/react-router";
import { LogOut } from "lucide-react";

export const Route = createFileRoute("/_auth/profile")({
  component: Profile,
});

function Profile() {
  const logout = useLogout();

  return (
    <>
      <CardHeader>
        <CardTitle>Profile</CardTitle>
        <CardDescription>Profile description</CardDescription>
      </CardHeader>
      <CardContent>
        <nav className="space-y-1">
          <Button variant="ghost" className="w-full justify-start font-normal">
            Account
          </Button>
          <Button
            variant="destructive"
            className="w-full font-normal flex justify-between"
            onClick={logout}
          >
            <span>Logout</span>
            <LogOut />
          </Button>
        </nav>
      </CardContent>
    </>
  );
}
