import { createFileRoute } from "@tanstack/react-router";

export const Route = createFileRoute("/_auth/tournament")({
  component: () => <div>Hello /_auth/tournamant!</div>,
});
