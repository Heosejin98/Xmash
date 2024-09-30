import { Check, Plus } from "lucide-react";
import { useQuery } from "@tanstack/react-query";
import { useState } from "react";
import { UserQueries } from "./user.queries";
import {
  Button,
  Command,
  CommandEmpty,
  CommandGroup,
  CommandInput,
  CommandItem,
  CommandList,
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/shared/ui";
import { cn } from "@/shared/lib/utils";

interface Props {
  values: string[];
  setValue: (value: string[]) => void;
}

export function UserSelect({ values, setValue }: Props) {
  const [open, setOpen] = useState(false);
  const { data = [] } = useQuery(UserQueries.userAllQuery());
  const [search, setSearch] = useState("");

  return (
    <Popover open={open} onOpenChange={setOpen}>
      <PopoverTrigger asChild>
        <Button
          className="rounded-full p-0 w-10 h-10"
          variant="outline"
          onClick={() => setOpen((prev) => !prev)}
        >
          <Plus></Plus>
        </Button>
      </PopoverTrigger>
      <PopoverContent className="w-[200px] p-0" side="left">
        <Command value={search} onValueChange={setSearch}>
          <CommandInput placeholder="Search Users..." />
          <CommandList>
            <CommandEmpty>No User found for {search}</CommandEmpty>
            <CommandGroup>
              {data.map((user) => (
                <CommandItem
                  key={user.userId}
                  value={user.userName + user.userId}
                  onSelect={() => {
                    setValue(
                      values.includes(user.userId)
                        ? values.filter((v) => v !== user.userId)
                        : [...values, user.userId]
                    );
                    setOpen(false);
                    setSearch("");
                  }}
                >
                  <Check
                    className={cn(
                      "mr-2 h-4 w-4",
                      values.includes(user.userId) ? "opacity-100" : "opacity-0"
                    )}
                  />
                  {user.userName}
                </CommandItem>
              ))}
            </CommandGroup>
          </CommandList>
        </Command>
      </PopoverContent>
    </Popover>
  );
}
