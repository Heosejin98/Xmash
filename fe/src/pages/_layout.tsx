import Header from "@/app/header.ui";
import BottomNavBar from "@/app/bottomNavBar.ui";
import { createFileRoute, Outlet } from "@tanstack/react-router";

export const Route = createFileRoute("/_layout")({
  component: () => (
    <>
      <Header></Header>
      <main className="p-2 flex-1">
        <Outlet />
      </main>
      <BottomNavBar></BottomNavBar>
    </>
  ),
});
