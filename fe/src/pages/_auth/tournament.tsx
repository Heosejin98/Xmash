import { createFileRoute } from "@tanstack/react-router";
import DepthHeader from "@/app/depthHeader.ui";

export const Route = createFileRoute("/_auth/tournament")({
  component: () => (
    <>
      <DepthHeader></DepthHeader>

      <div>Hello /_auth/tournamant!</div>
    </>
  ),
});
