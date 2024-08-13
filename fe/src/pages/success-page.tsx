import { createFileRoute } from "@tanstack/react-router";

export const Route = createFileRoute("/success-page")({
  component: () => <div>Hello /success_page!</div>,
});
