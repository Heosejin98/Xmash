import { GameQueries } from "@/entities/game/game.queries";
import { UserQueries } from "@/entities/user/user.queries";
import { useAuthStore } from "@/entities/user/user.store";
import { UserSelect } from "@/entities/user/userSelect.ui";
import { CreateGameDto, MatchType } from "@/shared/api/game";
import {
  Avatar,
  AvatarFallback,
  AvatarImage,
  Button,
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
  Input,
} from "@/shared/ui";
import { zodResolver } from "@hookform/resolvers/zod";
import { useQuery, useQueryClient } from "@tanstack/react-query";
import { useNavigate } from "@tanstack/react-router";
import { X } from "lucide-react";
import { Dispatch, forwardRef, SetStateAction } from "react";
import { useForm } from "react-hook-form";
import { toast } from "sonner";
import { useGameMutation } from "./add-game.mutation";
import { cn } from "@/shared/lib/utils";

interface Props {
  onSuccess?: () => void;
  onError?: (error: any) => void;
}

const UserListInput = forwardRef(
  (
    {
      value,
      onChange,
      exclude,
      className,
    }: {
      value: string[];
      onChange: Dispatch<SetStateAction<string[]>>;
      exclude?: string[];
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
            <div className="relative inline-block" key={user.userId}>
              <Avatar>
                <AvatarImage src={user.profileUrl ?? ""} alt={user.userName} />
                <AvatarFallback>{user.userName.slice(1, 3)}</AvatarFallback>
              </Avatar>
              <Button
                type="button"
                variant="destructive"
                size="icon"
                className="absolute -top-1 right-1 h-4 w-4 rounded-full"
                onClick={() => onChange(value.filter((v) => v !== user.userId))}
              >
                <X className="h-3 w-3"></X>
              </Button>
            </div>
          ))}

        {value.length < 2 && (
          <UserSelect values={value} setValue={onChange} exclude={exclude}></UserSelect>
        )}
      </div>
    );
  }
);

const useGameInfoForm = () => {
  const store = useAuthStore();
  return useForm<CreateGameDto>({
    resolver: async (values, context, options) => {
      const zodValidation = await zodResolver(CreateGameDto)(values, context, options);
      if (Object.keys(zodValidation.errors).length) {
        return zodValidation;
      }
      if (values.homeTeam.length !== values.awayTeam.length) {
        return {
          values: {},
          errors: {
            homeTeam: {
              type: "",
              message: "Home and Away team must have the same number of players",
            },
            awayTeam: {
              type: "",
              message: "Home and Away team must have the same number of players",
            },
          },
        };
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
      homeTeam: [store.user?.userId ?? ""],
      awayTeam: [],
      homeScore: 0,
      awayScore: 0,
      matchType: "single",
      gameType: "normal",
    },
  });
};

export const GameInfoForm = ({ onError, onSuccess }: Props) => {
  const form = useGameInfoForm();
  const { mutate } = useGameMutation();
  const client = useQueryClient();
  const navigate = useNavigate();

  const onSubmit = async (data: CreateGameDto) => {
    const req: CreateGameDto = {
      ...data,
      matchType: data.homeTeam.length === 1 ? MatchType.Enum.single : MatchType.Enum.double,
    };

    mutate(req, {
      onError: (error) => {
        console.log("onError", error);
        onError?.(error);

        toast.error(`Fail: ${error.message}`, {
          style: {
            backgroundColor: "#f44336",
            color: "#fff",
          },
        });
      },
      onSuccess: async () => {
        form.reset();
        onSuccess?.();
        client.invalidateQueries({ queryKey: GameQueries.keys[data.gameType] });

        toast.success("Game has been created successfully", {
          style: {
            backgroundColor: "#4caf50",
            color: "#fff",
          },
        });

        navigate({
          to: "/game",
          search: {
            gameType: req.gameType,
            matchType: req.matchType,
          },
        });
      },
    });
  };

  return (
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
                <FormLabel>Home Users</FormLabel>
                <FormControl>
                  <UserListInput {...field} exclude={form.getValues("awayTeam")}></UserListInput>
                </FormControl>
                <FormMessage></FormMessage>
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="awayTeam"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Away Users</FormLabel>
                <FormControl>
                  <UserListInput
                    {...field}
                    className="items-end flex-row-reverse"
                    exclude={form.getValues("homeTeam")}
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
                <FormLabel>Home Score</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    className="min-w-20 w-20"
                    type="number"
                    min={0}
                    max={50}
                    step={1}
                    autoComplete="off"
                    onChange={(e) => {
                      field.onChange(e.target.value.replace(/^0+/, ""));
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
                <FormLabel>Away Score</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    className="min-w-20 w-20"
                    type="number"
                    min={0}
                    max={50}
                    step={1}
                    autoComplete="off"
                    onChange={(e) => {
                      field.onChange(e.target.value.replace(/^0+/, ""));
                    }}
                  ></Input>
                </FormControl>
                <FormMessage></FormMessage>
              </FormItem>
            )}
          />
        </div>

        <div className="h-20 w-20"></div>

        <div className="flex justify-between">
          <Button
            type="submit"
            className="h-10"
            onClick={() => {
              form.setValue("gameType", "rank");
            }}
          >
            랭크 게임 등록
          </Button>
          <Button
            type="submit"
            variant="outline"
            className="h-10"
            onClick={() => {
              form.setValue("gameType", "normal");
            }}
          >
            노멀 게임 등록
          </Button>
        </div>
      </form>
    </Form>
  );
};
