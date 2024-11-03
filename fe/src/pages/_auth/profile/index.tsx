import { useLogout } from "@/features/login/logout.hook";
import { Button, Card, CardContent, CardHeader, CardTitle } from "@/shared/ui";
import { createFileRoute, Link } from "@tanstack/react-router";
import { LogOut } from "lucide-react";

export const Route = createFileRoute("/_auth/profile/")({
  component: Profile,
});

function Profile() {
  const logout = useLogout();

  return (
    <>
      <Card className="w-4/5 m-auto">
        <CardHeader>
          <CardTitle>설정</CardTitle>
        </CardHeader>
        <CardContent>
          <nav className="space-y-1">
            <Button variant="ghost" className="w-full justify-start font-normal" disabled>
              계정 정보
            </Button>
            <Link to="/profile/reset-password">
              <Button variant="ghost" className="w-full justify-start font-normal">
                비밀번호 변경
              </Button>
            </Link>
            <Button
              variant="destructive"
              className="w-full font-normal flex justify-between"
              onClick={logout}
            >
              <span>로그아웃</span>
              <LogOut />
            </Button>
          </nav>
          <p className="mt-10 text-right text-xs text-gray-400">
            앱 버전 {import.meta.env.VITE_APP_VERSION}
          </p>
        </CardContent>
      </Card>
    </>
  );
}
