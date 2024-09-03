import { RecordWidget } from "@/widgets/record/record.ui";
import { createFileRoute } from "@tanstack/react-router";
import { UserSearchSchema } from "./leader-board";

export const Route = createFileRoute("/record")({
  component: RecordWidget,
  validateSearch: UserSearchSchema,
});
