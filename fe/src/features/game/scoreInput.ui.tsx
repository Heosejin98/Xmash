import { UserQueries } from "@/entities/user/user.queries";
import { CreateGameDto, UpdateGameDto } from "@/shared/api/game";
import { cn } from "@/shared/lib/utils";
import {
  Avatar,
  AvatarFallback,
  AvatarImage,
  Button,
  Card,
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
  Input,
  CardHeader,
  CardTitle,
  CardDescription,
  CardContent,
} from "@/shared/ui";
import { zodResolver } from "@hookform/resolvers/zod";
import { useQuery } from "@tanstack/react-query";
import { useNavigate, useParams } from "@tanstack/react-router";
import { forwardRef, useState } from "react";
import { useForm } from "react-hook-form";
import { toast } from "sonner";
import { useGameMutation } from "./add-game.mutation";
import { ScoresInput } from "./context";
import DepthHeader from "@/app/depthHeader.ui";
import { useUpdateGameMutation } from "../update-game/update-game.mutation";

const UserListInput = forwardRef(
  (
    {
      value,
      className,
    }: {
      value: string[];
      className?: string;
    },
    _
  ) => {
    const { data = [] } = useQuery(UserQueries.userAllQuery());

    return (
      <div className={cn("flex gap-2", className)}>
        {data
          .filter((user) => value.includes(user.userId))
          .map((user) => (
            <div className={cn("relative inline-block", className)} key={user.userId}>
              <Avatar>
                <AvatarImage src={user.profileUrl ?? ""} alt={user.userName} />
                <AvatarFallback>{user.userName.slice(1, 3)}</AvatarFallback>
              </Avatar>
            </div>
          ))}
      </div>
    );
  }
);

const useGameInfoForm = ({ awayTeam, homeTeam, matchType, awayScore, homeScore }: ScoresInput) => {
  return useForm<CreateGameDto>({
    resolver: async (values, context, options) => {
      const zodValidation = await zodResolver(CreateGameDto)(values, context, options);
      if (Object.keys(zodValidation.errors).length) {
        return zodValidation;
      }

      if (values.awayScore < 11 && values.homeScore < 11) {
        return {
          values: {},
          errors: {
            homeScore: {
              type: "",
              message: "One of the scores must be over 11",
            },
            awayScore: {
              type: "",
              message: "One of the scores must be over 11",
            },
          },
        };
      }

      if (values.homeScore === values.awayScore) {
        return {
          values: {},
          errors: {
            homeScore: {
              type: "",
              message: "Home and Away score must be different",
            },
            awayScore: {
              type: "",
              message: "Home and Away score must be different",
            },
          },
        };
      }

      return {
        values,
        errors: {},
      };
    },
    defaultValues: {
      homeTeam,
      awayTeam,
      matchType,
      homeScore: homeScore ?? 0,
      awayScore: awayScore ?? 0,
    },
  });
};

export const ScoreInputForm = ({ onPrev, ...formData }: ScoresInput & { onPrev: () => void }) => {
  const form = useGameInfoForm(formData);
  const { mutate: createGame } = useGameMutation();
  const { mutate: updateGame } = useUpdateGameMutation();
  const navigate = useNavigate();
  const [tmpValue, onChangeTmpValue] = useState(0);
  const { gameId } = useParams({
    strict: false,
  });

  const onSubmit = async (data: CreateGameDto | UpdateGameDto) => {
    if (gameId) {
      updateGame(
        {
          gameId,
          updateGameDto: {
            ...data,
          },
        },
        {
          onError: (error) => {
            console.log("onError", error);

            toast.error(`Fail: ${error.message}`, {
              style: {
                backgroundColor: "#f44336",
                color: "#fff",
              },
            });
          },
          onSuccess: async () => {
            form.reset();

            toast.success("Game has been created successfully", {
              style: {
                backgroundColor: "#4caf50",
                color: "#fff",
              },
            });

            navigate({
              to: "/game",
              replace: true,
              search: {
                matchType: data.matchType,
              },
            });
          },
        }
      );
    } else {
      createGame(data, {
        onError: (error) => {
          console.log("onError", error);

          toast.error(`Fail: ${error.message}`, {
            style: {
              backgroundColor: "#f44336",
              color: "#fff",
            },
          });
        },
        onSuccess: async () => {
          form.reset();

          toast.success("Game has been created successfully", {
            style: {
              backgroundColor: "#4caf50",
              color: "#fff",
            },
          });

          navigate({
            to: "/game",
            replace: true,
            search: {
              matchType: data.matchType,
            },
          });
        },
      });
    }
  };

  return (
    <>
      <DepthHeader onPrev={onPrev}></DepthHeader>
      <Card className="m-4 h-full">
        <CardHeader>
          <CardTitle>점수 입력</CardTitle>
          <CardDescription>점수를 입력해 주세요.</CardDescription>
        </CardHeader>
        <CardContent className="flex flex-col gap-4 flex-1">
          <Form {...form}>
            <form
              id="game-info"
              className="grid w-full items-center gap-4 p-2"
              onSubmit={form.handleSubmit(onSubmit)}
            >
              <div className="flex justify-between">
                <FormField
                  control={form.control}
                  name="homeTeam"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Home</FormLabel>
                      <FormControl>
                        <UserListInput {...field}></UserListInput>
                      </FormControl>
                      <FormMessage></FormMessage>
                    </FormItem>
                  )}
                />
                <FormField
                  control={form.control}
                  name="awayTeam"
                  render={({ field }) => (
                    <FormItem className="flex flex-col items-end">
                      <FormLabel>Away</FormLabel>
                      <FormControl>
                        <UserListInput
                          {...field}
                          className="items-end flex-row-reverse"
                        ></UserListInput>
                      </FormControl>
                      <FormMessage></FormMessage>
                    </FormItem>
                  )}
                />
              </div>

              <div className="flex justify-between">
                <FormField
                  control={form.control}
                  name="homeScore"
                  render={({ field }) => (
                    <FormItem className="flex flex-col">
                      <FormLabel>Score</FormLabel>
                      <FormControl>
                        <Input
                          {...field}
                          className="min-w-20 w-20"
                          type="number"
                          min={0}
                          max={50}
                          step={1}
                          inputMode="numeric"
                          onFocus={() => {
                            onChangeTmpValue(field.value);
                            field.onChange("");
                          }}
                          onBlur={() => {
                            if (!field.value) {
                              field.onChange(tmpValue);
                            }
                          }}
                          onChange={(e) => {
                            field.onChange(e.target.value.replace(/^0+(\d+)/, "$1"));
                          }}
                        ></Input>
                      </FormControl>
                      <FormMessage></FormMessage>
                    </FormItem>
                  )}
                />
                <FormField
                  control={form.control}
                  name="awayScore"
                  render={({ field }) => (
                    <FormItem className="flex flex-col items-end">
                      <FormLabel>Score</FormLabel>
                      <FormControl>
                        <Input
                          {...field}
                          className="min-w-20 w-20"
                          type="number"
                          min={0}
                          max={50}
                          step={1}
                          inputMode="numeric"
                          onFocus={() => {
                            onChangeTmpValue(field.value);
                            field.onChange("");
                          }}
                          onBlur={() => {
                            if (!field.value) {
                              field.onChange(tmpValue);
                            }
                          }}
                          onChange={(e) => {
                            field.onChange(e.target.value.replace(/^0+(\d+)/, "$1"));
                          }}
                        ></Input>
                      </FormControl>
                      <FormMessage className="text-right"></FormMessage>
                    </FormItem>
                  )}
                />
              </div>

              <div className="h-20 w-20"></div>

              <Button type="submit" className="h-10 p-0">
                {gameId ? "수정 완료" : "게임 등록"}
              </Button>
            </form>
          </Form>
        </CardContent>
      </Card>
    </>
  );
};
