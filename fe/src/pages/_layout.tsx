import Header from "@/app/header.ui";
import BottomNavBar from "@/app/bottomNavBar.ui";
import { createFileRoute, Outlet } from "@tanstack/react-router";

export const Route = createFileRoute("/_layout")({
  component: () => (
    <>
      <div>
        <Header></Header>
        <main className="p-2 flex-1 mb-nav">
          <Outlet />
        </main>
        <BottomNavBar></BottomNavBar>
      </div>
    </>
  ),
});
