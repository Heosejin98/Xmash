import {
  Drawer,
  DrawerTrigger,
  DrawerClose,
  DrawerContent,
  DrawerHeader,
  DrawerFooter,
  DrawerTitle,
  DrawerDescription,
  Button,
  Switch,
  Label,
} from "@/shared/ui";
import { Plus } from "lucide-react";

export function AddGameButton() {
  return (
    <Drawer>
      <DrawerTrigger asChild>
        <Button className="rounded-full p-0 w-10 h-10" variant="outline">
          <Plus></Plus>
        </Button>
      </DrawerTrigger>
      <DrawerContent>
        <div className="mx-auto w-full max-w-sm">
          <DrawerHeader>
            <DrawerTitle>경기 등록 </DrawerTitle>
            <DrawerDescription>선수와 점수를 설정하세요.</DrawerDescription>
          </DrawerHeader>
          <div className="p-4 pb-0">
            <form>
              <div className="flex items-center space-x-2">
                <Switch id="airplane-mode" />
                <Label htmlFor="airplane-mode">Rank Mode</Label>
              </div>

              <div className="mb-4">
                <label className="block text-sm font-medium text-gray-700">선수</label>
                <select
                  id="player"
                  name="player"
                  className="mt-1 block w-full pl-3 pr-10 py-2 text-base border-gray-300 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm rounded-md"
                >
                  <option>선수1</option>
                  <option>선수2</option>
                  <option>선수3</option>
                </select>
              </div>
              <div className="mb-4">
                <label className="block text-sm font-medium text-gray-700">점수</label>
                <input
                  type="number"
                  name="score"
                  id="score"
                  className="mt-1 block w-full pl-3 pr-10 py-2 text-base border-gray-300 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm rounded-md"
                />
              </div>
            </form>
          </div>
          <DrawerFooter>
            <Button>Submit </Button>
            <DrawerClose asChild>
              <Button variant="outline"> Cancel </Button>
            </DrawerClose>
          </DrawerFooter>
        </div>
      </DrawerContent>
    </Drawer>
  );
}
