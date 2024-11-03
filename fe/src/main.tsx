import React from "react";
import ReactDOM from "react-dom/client";
import {
  RouterProvider,
  createRouter,
  parseSearchWith,
  stringifySearchWith,
} from "@tanstack/react-router";
import "./index.css";

// Import the generated route tree
import { routeTree } from "./routeTree.gen";
import { QueryClientProvider } from "@tanstack/react-query";
import { useAuthStore } from "./entities/user/user.store";
import { queryClient } from "./shared/lib/react-query";
import { decodeFromBinary, encodeToBinary } from "./shared/lib/utils";

// Create a new router instance
const router = createRouter({
  routeTree,
  context: { isAuthenticated: false },
  parseSearch: parseSearchWith((value) => JSON.parse(decodeFromBinary(value))),
  stringifySearch: stringifySearchWith((value) => encodeToBinary(JSON.stringify(value))),
});

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

  document.title = import.meta.env.MODE === "development" ? "Dev" : "Xmash";

  root.render(
    <React.StrictMode>
      <QueryClientProvider client={queryClient}>
        <InnerApp />
      </QueryClientProvider>
    </React.StrictMode>
  );
}
