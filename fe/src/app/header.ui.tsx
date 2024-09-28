import { useAuthStore } from "@/entities/user/user.store";
import { Avatar, AvatarFallback, AvatarImage, Button } from "@/shared/ui";
import { useRouter } from "@tanstack/react-router";
import { UserRound } from "lucide-react";

const Header = () => {
  const { user, isAuthenticated } = useAuthStore();
  const router = useRouter();

  const routeLogin = () => {
    router.navigate({
      to: "/login",
      search: {
        redirect: router.state.location.href,
      },
    });
  };

  return (
    <header className="flex justify-between items-center px-4 pt-4">
      <h1 className="font-bold text-xl">Xmash</h1>

      {isAuthenticated && user ? (
        <Button size="icon" variant="outline" className="rounded-full">
          <Avatar>
            <AvatarImage src={user.profileUrl ?? ""} alt={user.userName} />
            <AvatarFallback>{user.userName.slice(1, 3)}</AvatarFallback>
          </Avatar>
        </Button>
      ) : (
        <Button onClick={routeLogin} size="icon" variant="outline" className="rounded-full">
          <UserRound></UserRound>
        </Button>
      )}
    </header>
  );
};

export default Header;
