import { Button } from "@/shared/ui";
import { useRouter } from "@tanstack/react-router";
import { ChevronLeft } from "lucide-react";
import { ReactNode } from "react";

const DepthHeader = ({ children, onPrev }: { children?: ReactNode; onPrev?: () => void }) => {
  const router = useRouter();
  const goBack = () => {
    if (onPrev) {
      onPrev();
    } else {
      router.history.back();
    }
  };

  return (
    <header className="flex items-center pt-4">
      <Button variant="ghost">
        <ChevronLeft onClick={goBack} />
      </Button>
      {children}
    </header>
  );
};

export default DepthHeader;
