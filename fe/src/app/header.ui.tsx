import { useAuthStore } from "@/entities/user/user.store";
import { Button } from "@/shared/ui";
import { redirect } from "@tanstack/react-router";
import { UserRound } from "lucide-react";

const Header = () => {
  const store = useAuthStore();

  const throwRedirect = () => {
    throw redirect({
      to: "/login",
      replace: true,
      search: {
        redirect: location.href,
      },
    });
  };

  return (
    <header className="flex justify-between">
      <h1>Xmash</h1>

      {store.isAuthenticated ? (
        <Button onClick={() => store.setAuthenticated(false)}>Logout</Button>
      ) : (
        <Button onClick={throwRedirect}>
          <UserRound></UserRound>
        </Button>
      )}
    </header>
  );
};

export default Header;
