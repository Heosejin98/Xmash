import { UserQueries } from "@/entities/user/user.queries";
import { UserSelect } from "@/entities/user/userSelect.ui";
import { CreateGameDto, GameType, MatchType } from "@/shared/api/game";
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
  Select,
  SelectContent,
  SelectGroup,
  SelectItem,
  SelectTrigger,
  SelectValue,
  Tabs,
  TabsContent,
  TabsList,
  TabsTrigger,
} from "@/shared/ui";
import { zodResolver } from "@hookform/resolvers/zod";
import { Separator } from "@radix-ui/react-select";
import { useQuery, useQueryClient } from "@tanstack/react-query";
import { X } from "lucide-react";
import { Dispatch, forwardRef, SetStateAction } from "react";
import { useForm } from "react-hook-form";
import { toast } from "sonner";
import { useGameMutation } from "./add-game.mutation";
import { GameQueries } from "@/entities/game/game.queries";
import { useAuthStore } from "@/entities/user/user.store";

interface Props {
  onSuccess?: () => void;
  onError?: (error: any) => void;
}

const MatchTypeInput = forwardRef(
  ({ ...props }: { value: string; onChange: (v: string) => void }, _) => (
    <Select name="matchType" value={props.value} onValueChange={props.onChange}>
      <SelectTrigger className="w-[180px]">
        <SelectValue placeholder="Select MatchType" />
      </SelectTrigger>
      <SelectContent>
        <SelectGroup>
          {Object.values(MatchType.Values)
            .filter((v) => v !== "all")
            .map((value) => (
              <SelectItem key={value} value={value}>
                {value}
              </SelectItem>
            ))}
        </SelectGroup>
      </SelectContent>
    </Select>
  )
);

const UserListInput = forwardRef(
  (
    {
      value,
      onChange,
      matchType,
      exclude,
    }: {
      value: string[];
      onChange: Dispatch<SetStateAction<string[]>>;
      matchType: MatchType;
      exclude?: string[];
    },
    _
  ) => {
    const { data = [] } = useQuery(UserQueries.userAllQuery());

    return (
      <div className="flex">
        {data
          .filter((user) => value.includes(user.userId))
          .map((user) => (
            <div className="relative inline-block" key={user.userId}>
              <Avatar className="mr-2">
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

        {((matchType === "double" && value.length < 2) ||
          (matchType === "single" && value.length < 1)) && (
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

  const onSubmit = async (data: CreateGameDto) => {
    mutate(data, {
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
      },
    });
  };

  return (
    <Tabs
      defaultValue="normal"
      className="w-full"
      onValueChange={(type) => form.setValue("gameType", type as GameType)}
    >
      <TabsList className="grid grid-cols-2">
        <TabsTrigger value="normal">Normal</TabsTrigger>
        <TabsTrigger value="rank">Rank</TabsTrigger>
      </TabsList>

      <Separator className="my-8" />

      <TabsContent value="normal">
        <Form {...form}>
          <form
            id="game-info"
            className="grid w-full items-center gap-4"
            onSubmit={form.handleSubmit(onSubmit)}
          >
            <FormField
              control={form.control}
              name="matchType"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>MatchType</FormLabel>
                  <FormControl>
                    <MatchTypeInput {...field}></MatchTypeInput>
                  </FormControl>
                  <FormMessage></FormMessage>
                </FormItem>
              )}
            />

            <div className="flex justify-between">
              <FormField
                control={form.control}
                name="homeTeam"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Home Users</FormLabel>
                    <FormControl>
                      <UserListInput
                        {...field}
                        matchType={form.getValues("matchType")}
                        exclude={form.getValues("awayTeam")}
                      ></UserListInput>
                    </FormControl>
                    <FormMessage></FormMessage>
                  </FormItem>
                )}
              />

              <FormField
                control={form.control}
                name="homeScore"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Home Score</FormLabel>
                    <FormControl>
                      <Input
                        {...field}
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

            <div className="flex justify-between">
              <FormField
                control={form.control}
                name="awayTeam"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Away Users</FormLabel>
                    <FormControl>
                      <UserListInput
                        {...field}
                        matchType={form.getValues("matchType")}
                        exclude={form.getValues("homeTeam")}
                      ></UserListInput>
                    </FormControl>
                    <FormMessage></FormMessage>
                  </FormItem>
                )}
              />

              <FormField
                control={form.control}
                name="awayScore"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Away Score</FormLabel>
                    <FormControl>
                      <Input
                        {...field}
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
          </form>
        </Form>
      </TabsContent>

      <TabsContent value="rank">
        <Form {...form}>
          <form
            id="game-info"
            className="grid w-full items-center gap-4"
            onSubmit={form.handleSubmit(onSubmit)}
          >
            <FormField
              control={form.control}
              name="matchType"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>MatchType</FormLabel>
                  <FormControl>
                    <MatchTypeInput {...field}></MatchTypeInput>
                  </FormControl>
                  <FormMessage></FormMessage>
                </FormItem>
              )}
            />

            <div className="flex justify-between">
              <FormField
                control={form.control}
                name="homeTeam"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Home Users</FormLabel>
                    <FormControl>
                      <UserListInput
                        {...field}
                        matchType={form.getValues("matchType")}
                        exclude={form.getValues("awayTeam")}
                      ></UserListInput>
                    </FormControl>
                    <FormMessage></FormMessage>
                  </FormItem>
                )}
              />

              <FormField
                control={form.control}
                name="homeScore"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Home Score</FormLabel>
                    <FormControl>
                      <Input
                        {...field}
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

            <div className="flex justify-between">
              <FormField
                control={form.control}
                name="awayTeam"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Away Users</FormLabel>
                    <FormControl>
                      <UserListInput
                        {...field}
                        matchType={form.getValues("matchType")}
                        exclude={form.getValues("homeTeam")}
                      ></UserListInput>
                    </FormControl>
                    <FormMessage></FormMessage>
                  </FormItem>
                )}
              />

              <FormField
                control={form.control}
                name="awayScore"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Away Score</FormLabel>
                    <FormControl>
                      <Input
                        {...field}
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
          </form>
        </Form>
      </TabsContent>
    </Tabs>
  );
};
