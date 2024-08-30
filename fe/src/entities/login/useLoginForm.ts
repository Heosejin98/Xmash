import { FormSchema } from "@/entities/login";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";

export const useLoginForm = () => useForm<FormSchema>({
  resolver: zodResolver(FormSchema),
  defaultValues: {
    userId: "",
    password: "",
  },
});
