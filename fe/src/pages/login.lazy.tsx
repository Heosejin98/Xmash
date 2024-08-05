import { LoginForm } from "@/widgets/login/loginForm.ui";
import { createLazyFileRoute } from "@tanstack/react-router";

export const Route = createLazyFileRoute("/login")({ component: () => <LoginForm /> });
