import { useAuthStore } from "@/entities/user/user.store";
import { Avatar, AvatarFallback, AvatarImage } from "@/shared/ui";
import { Link, useRouter } from "@tanstack/react-router";
import { UserRound } from "lucide-react";

const Header = () => {
  const { user, isAuthenticated } = useAuthStore();
  const router = useRouter();

  return (
    <header className="flex justify-between items-center px-4 pt-4 sticky bg-white z-10 top-0">
      <h1 className="font-bold text-xl">
        <Link to="/">Xmash</Link>
      </h1>

      {isAuthenticated && user ? (
        <Link to="/profile" className="rounded-full border p-1">
          <Avatar>
            <AvatarImage src={user.profileUrl ?? ""} alt={user.userName} />
            <AvatarFallback>{user.userName.slice(1, 3)}</AvatarFallback>
          </Avatar>
        </Link>
      ) : (
        <Link
          to="/login"
          search={(prev) => ({ ...prev, redirect: router.state.location.href })}
          className="rounded-full border p-1"
        >
          <UserRound></UserRound>
        </Link>
      )}
    </header>
  );
};

export default Header;
