import { LoginUserDto } from "@/shared/api/auth";
import {
  Button,
  Card,
  CardContent,
  CardFooter,
  CardHeader,
  CardTitle,
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
  Input,
} from "@/shared/ui";
import { zodResolver } from "@hookform/resolvers/zod";
import { useNavigate } from "@tanstack/react-router";
import { useForm } from "react-hook-form";
import { useLoginMutation } from "./login.mutation";
import { useState } from "react";
import { Eye, EyeOff } from "lucide-react";

const useLoginForm = () =>
  useForm<LoginUserDto>({
    resolver: zodResolver(LoginUserDto),
    defaultValues: {
      userId: "",
      password: "",
    },
  });

const useShowPassword = () => {
  const [showPassword, setShowPassword] = useState<"text" | "password">("password");

  const toggleShowPassword = () => {
    setShowPassword((prev) => (prev === "password" ? "text" : "password"));
  };

  return { showPassword, toggleShowPassword };
};

export function LoginForm() {
  const form = useLoginForm();
  const { mutate } = useLoginMutation();
  const navigate = useNavigate();
  const { showPassword, toggleShowPassword } = useShowPassword();

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
    <Card className="w-4/5 m-auto">
      <CardHeader>
        <CardTitle>Login</CardTitle>
        {/* <CardDescription>Deploy your new project in one-click.</CardDescription> */}
      </CardHeader>
      <CardContent>
        <Form {...form}>
          <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-4" id="loginForm">
            <FormField
              control={form.control}
              name="userId"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Username</FormLabel>
                  <FormControl>
                    <Input placeholder="username" autoComplete="username" {...field} />
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
                    <div className="relative">
                      <Input
                        type={showPassword}
                        placeholder="password"
                        autoComplete="current-password"
                        className="pr-10"
                        maxLength={25}
                        {...field}
                      />
                      {field.value && (
                        <Button
                          variant="ghost"
                          className="absolute right-2 p-0 top-0"
                          type="button"
                          onClick={toggleShowPassword}
                        >
                          {showPassword === "password" ? <Eye></Eye> : <EyeOff></EyeOff>}
                        </Button>
                      )}
                    </div>
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
          </form>
        </Form>
      </CardContent>
      <CardFooter className="flex flex-row-reverse">
        <Button type="submit" form="loginForm">
          Submit
        </Button>
      </CardFooter>
    </Card>
  );
}
