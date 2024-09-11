import { LoginUserDto } from "@/shared/api/auth";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";

export const useLoginForm = () => useForm<LoginUserDto>({
  resolver: zodResolver(LoginUserDto),
  defaultValues: {
    userId: "",
    password: "",
  },
});
