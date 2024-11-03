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
              message: "비밀번호가 일치하지 않습니다.",
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
        toast.error(error.response?.data?.message, {
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
        <CardTitle className="text-2xl font-bold">비밀번호 변경</CardTitle>
        <CardDescription>
          현재 비밀번호와 새 비밀번호를 입력하여 계정을 업데이트하십시오.
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
                  <FormLabel>현재 비밀번호</FormLabel>
                  <FormControl>
                    <Input
                      type="password"
                      placeholder="현재 비밀번호"
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
                  <FormLabel>새로운 비밀번호</FormLabel>
                  <FormControl>
                    <Input
                      type="password"
                      placeholder="새로운 비밀번호"
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
                  <FormLabel>비밀번호 확인 </FormLabel>
                  <FormControl>
                    <Input
                      type="password"
                      placeholder="비밀번호 확인"
                      autoComplete="Confirm Password"
                      {...field}
                    />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />

            <Button type="submit" className="w-full">
              비밀번호 변경하기
            </Button>
          </form>
        </Form>
      </CardContent>
    </>
  );
}
