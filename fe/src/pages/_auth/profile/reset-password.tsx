import { useResetPasswordMutation } from "@/features/login/reset-password.mutaion";
import { PasswordResetDto } from "@/shared/api/auth";
import {
  Button,
  CardContent,
  CardDescription,
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
import { createFileRoute, useRouter } from "@tanstack/react-router";
import { useForm } from "react-hook-form";
import { toast } from "sonner";

export const Route = createFileRoute("/_auth/profile/reset-password")({
  component: ResetPassword,
});

const useResetForm = () =>
  useForm<PasswordResetDto>({
    resolver: async (values, context, options) => {
      const zodValidation = await zodResolver(PasswordResetDto)(values, context, options);
      if (Object.keys(zodValidation.errors).length) {
        return zodValidation;
      }
      if (values.newPassword !== values.confirmNewPassword) {
        return {
          values,
          errors: {
            confirmNewPassword: {
              message: "Passwords do not match",
              type: "manual",
            },
          },
        };
      }

      return {
        values,
        errors: {},
      };
    },
  });

function ResetPassword() {
  const form = useResetForm();
  const { mutate } = useResetPasswordMutation();
  const router = useRouter();
  const goBack = () => router.history.back();

  const onSubmit = async (data: PasswordResetDto) => {
    mutate(data, {
      onError: (error) => {
        console.log("onError", error);
        toast.error("Failed to update password", {
          style: {
            backgroundColor: "#f44336",
            color: "#fff",
          },
        });
      },
      onSuccess: async () => {
        toast.success("Password updated", {
          style: {
            backgroundColor: "#4caf50",
            color: "#fff",
          },
        });
        goBack();
      },
    });
  };

  return (
    <>
      <CardHeader className="space-y-1">
        <CardTitle className="text-2xl font-bold">Change Password</CardTitle>
        <CardDescription>
          Enter your current and new password to update your account
        </CardDescription>
      </CardHeader>
      <CardContent>
        <Form {...form}>
          <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-4">
            <FormField
              control={form.control}
              name="prevPassword"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Prev Password</FormLabel>
                  <FormControl>
                    <Input
                      type="password"
                      placeholder="Prev Password"
                      autoComplete="Prev Password"
                      {...field}
                    />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />

            <FormField
              control={form.control}
              name="newPassword"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>New Password</FormLabel>
                  <FormControl>
                    <Input
                      type="password"
                      placeholder="New Password"
                      autoComplete="New Password"
                      {...field}
                    />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />

            <FormField
              control={form.control}
              name="confirmNewPassword"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Confirm Password</FormLabel>
                  <FormControl>
                    <Input
                      type="password"
                      placeholder="Confirm Password"
                      autoComplete="Confirm Password"
                      {...field}
                    />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />

            <Button type="submit" className="w-full">
              Update Password
            </Button>
          </form>
        </Form>
      </CardContent>
    </>
  );
}
