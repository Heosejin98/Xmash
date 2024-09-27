import {
  Button,
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
  Input,
} from "@/shared/ui";
import { useNavigate, useRouter } from "@tanstack/react-router";
import { useLoginMutation } from "./login.mutation";
import { LoginUserDto } from "@/shared/api/auth";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";

const useLoginForm = () =>
  useForm<LoginUserDto>({
    resolver: zodResolver(LoginUserDto),
    defaultValues: {
      userId: "",
      password: "",
    },
  });

export function LoginForm() {
  const form = useLoginForm();
  const { mutate } = useLoginMutation();
  const navigate = useNavigate();

  const onSubmit = async (data: LoginUserDto) => {
    mutate(data, {
      onError: (error) => {
        console.log("onError", error);
      },
      onSuccess: async () => {
        await navigate({
          to: "/",
          replace: true,
        });
      },
    });
  };

  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-6">
        <FormField
          control={form.control}
          name="userId"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Username</FormLabel>
              <FormControl>
                <Input placeholder="Email address" autoComplete="username" {...field} />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <FormField
          control={form.control}
          name="password"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Password</FormLabel>
              <FormControl>
                <Input
                  type="password"
                  placeholder="password"
                  autoComplete="current-password"
                  {...field}
                />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <Button type="submit">Submit</Button>
      </form>
    </Form>
  );
}
