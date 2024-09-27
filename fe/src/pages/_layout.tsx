import Header from "@/app/header.ui";
import NavBar from "@/app/navBar.ui";
import { createFileRoute, Outlet } from "@tanstack/react-router";

export const Route = createFileRoute("/_layout")({
  component: () => (
    <>
      <Header></Header>
      <main className="p-2 h-full">
        <Outlet />
      </main>
      <NavBar></NavBar>
    </>
  ),
});
