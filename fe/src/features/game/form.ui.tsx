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
  SelectLabel,
  SelectTrigger,
  SelectValue,
  Tabs,
  TabsContent,
  TabsList,
  TabsTrigger,
} from "@/shared/ui";
import { zodResolver } from "@hookform/resolvers/zod";
import { Separator } from "@radix-ui/react-select";
import { useQuery } from "@tanstack/react-query";
import { X } from "lucide-react";
import { forwardRef } from "react";
import { useForm } from "react-hook-form";
import { useGameMutation } from "./game.mutation";

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
          <SelectLabel>MatchType</SelectLabel>
          {Object.values(MatchType.Values).map((value) => (
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
    }: {
      value: string[];
      onChange: (v: string[]) => void;
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

        {value.length < 2 && <UserSelect values={value} setValue={onChange}></UserSelect>}
      </div>
    );
  }
);

const useGameInfoForm = () =>
  useForm<CreateGameDto>({
    resolver: zodResolver(CreateGameDto),
    defaultValues: {
      winTeam: [],
      loseTeam: [],
      homeScore: 0,
      awayScore: 0,
      matchType: "single",
      gameType: "normal",
      point: null,
    },
  });

export const GameInfoForm = ({ onError, onSuccess }: Props) => {
  const form = useGameInfoForm();
  const { mutate } = useGameMutation();

  const onSubmit = async (data: CreateGameDto) => {
    mutate(data, {
      onError: (error) => {
        console.log("onError", error);
        onError && onError(error);
      },
      onSuccess: async () => {
        form.reset();
        onSuccess && onSuccess();
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
                name="winTeam"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Win Users</FormLabel>
                    <FormControl>
                      <UserListInput {...field}></UserListInput>
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
                name="loseTeam"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Lose Users</FormLabel>
                    <FormControl>
                      <UserListInput {...field}></UserListInput>
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
                        value={field.value}
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
                name="winTeam"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Win Users</FormLabel>
                    <FormControl>
                      <UserListInput {...field}></UserListInput>
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
                name="loseTeam"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Lose Users</FormLabel>
                    <FormControl>
                      <UserListInput {...field}></UserListInput>
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
                        value={field.value}
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
