import DepthHeader from "@/app/depthHeader.ui";
import { LoginForm } from "@/features/login/login.ui";
import { createFileRoute, redirect } from "@tanstack/react-router";
import { z } from "zod";

const fallback = "/" as const;

export const Route = createFileRoute("/login")({
  validateSearch: z.object({
    redirect: z.string().optional().catch(""),
  }),
  beforeLoad: ({ context, search }) => {
    if (context.isAuthenticated) {
      throw redirect({ to: search.redirect || fallback });
    }
  },
  component: () => {
    return (
      <>
        <DepthHeader>
          <h2 className="font-semibold text-lg">Login</h2>
        </DepthHeader>
        <LoginForm />
      </>
    );
  },
});
