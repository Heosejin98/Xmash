import {
  Button,
  Drawer,
  DrawerClose,
  DrawerContent,
  DrawerDescription,
  DrawerFooter,
  DrawerHeader,
  DrawerTitle,
  DrawerTrigger,
  ScrollArea,
} from "@/shared/ui";
import { Plus } from "lucide-react";
import { GameInfoForm } from "./form.ui";
import { useState } from "react";

export function AddGameButton() {
  const [open, setOpen] = useState(false);

  return (
    <Drawer open={open} onOpenChange={setOpen}>
      <DrawerTrigger asChild>
        <Button className="rounded-full w-8 h-8" size="icon">
          <Plus></Plus>
        </Button>
      </DrawerTrigger>
      <DrawerContent>
        <div className="mx-auto w-full max-w-sm max-h-[95%]">
          <DrawerHeader>
            <DrawerTitle>경기 등록</DrawerTitle>
            <DrawerDescription></DrawerDescription>
          </DrawerHeader>

          <ScrollArea className="overflow-y-auto">
            <div className="p-4">
              <GameInfoForm onSuccess={() => setOpen(false)}></GameInfoForm>
            </div>
          </ScrollArea>
        </div>
        <DrawerFooter>
          <Button type="submit" form="game-info">
            Submit
          </Button>
          <DrawerClose asChild>
            <Button variant="outline">Cancel</Button>
          </DrawerClose>
        </DrawerFooter>
      </DrawerContent>
    </Drawer>
  );
}
