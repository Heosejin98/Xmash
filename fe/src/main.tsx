import React from "react";
import ReactDOM from "react-dom/client";
import { RouterProvider, createRouter } from "@tanstack/react-router";
import "./index.css";

// Import the generated route tree
import { routeTree } from "./routeTree.gen";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { useAuthStore } from "./features/user/useAuthStore";

// Create a new router instance
const router = createRouter({ routeTree, context: { isAuthenticated: false } });
const queryClient = new QueryClient();

// Register the router instance for type safety
declare module "@tanstack/react-router" {
  interface Register {
    router: typeof router;
  }
}

const InnerApp = () => {
  const { isAuthenticated } = useAuthStore();

  return <RouterProvider router={router} context={{ isAuthenticated }} />;
};

// Render the app
const rootElement = document.getElementById("root")!;
if (!rootElement.innerHTML) {
  const root = ReactDOM.createRoot(rootElement);
  root.render(
    <React.StrictMode>
      <QueryClientProvider client={queryClient}>
        <InnerApp />
      </QueryClientProvider>
    </React.StrictMode>
  );
}
