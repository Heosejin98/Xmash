import { CreateGameDto, GameType, MatchType } from "@/shared/api/game";
import {
  Button,
  Tabs,
  TabsList,
  TabsContent,
  TabsTrigger,
  Select,
  SelectContent,
  SelectGroup,
  SelectItem,
  SelectLabel,
  SelectTrigger,
  SelectValue,
  Avatar,
  AvatarImage,
  AvatarFallback,
  Form,
  FormMessage,
  FormControl,
  FormItem,
  FormLabel,
  FormField,
} from "@/shared/ui";
import { Separator } from "@radix-ui/react-select";
import { Plus } from "lucide-react";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { useGameMutation } from "./game.mutation";
import { UserDto } from "@/shared/api/user";
import { forwardRef } from "react";
import { UserSelect } from "@/entities/user/userSelect.ui";

interface Props {}

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

const UserListInput = forwardRef(({ value, onChange }: {}, _) => {
  const userList: UserDto[] = [];
  return (
    <div className="flex">
      {userList.map((user) => (
        <Avatar className="mr-2">
          <AvatarImage src={user.profileUrl ?? ""} alt={user.userName} />
          <AvatarFallback>{user.userName.slice(1, 3)}</AvatarFallback>
        </Avatar>
      ))}
      {userList.length < 2 && (
        <UserSelect></UserSelect>
        // <Button className="rounded-full p-0 w-10 h-10" variant="outline">
        //   <Plus></Plus>
        // </Button>
      )}
    </div>
  );
});

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

export const GameInfoForm = ({}: Props) => {
  const form = useGameInfoForm();
  const { mutate } = useGameMutation();

  const onSubmit = async (data: CreateGameDto) => {
    console.log(data);
    // mutate(data, {
    //   onError: (error) => {
    //     console.log("onError", error);
    //   },
    //   onSuccess: async () => {
    //     // TODO: close drawer
    //   },
    // });
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
      {/* <TabsContent value="normal">Make changes to your normal here.</TabsContent>
  <TabsContent value="rank">Change your rank here.</TabsContent> */}
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
          </form>
        </Form>
      </TabsContent>

      <TabsContent value="rank">
        {/* <MatchTypeInput matchType={matchType} setMatchType={setMatchType}></MatchTypeInput> */}
      </TabsContent>
    </Tabs>
  );
};
