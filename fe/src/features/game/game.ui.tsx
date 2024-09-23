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
} from "@/shared/ui";
import { Plus } from "lucide-react";
import { GameInfoForm } from "./form.ui";

export function AddGameButton() {
  return (
    <Drawer>
      <DrawerTrigger asChild>
        <Button className="rounded-full w-10 h-10" size="icon">
          <Plus></Plus>
        </Button>
      </DrawerTrigger>
      <DrawerContent>
        <div className="mx-auto w-full max-w-sm">
          <DrawerHeader>
            <DrawerTitle>경기 등록</DrawerTitle>
            <DrawerDescription></DrawerDescription>
          </DrawerHeader>
          <div className="p-4 pb-0">
            <GameInfoForm></GameInfoForm>
          </div>
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
