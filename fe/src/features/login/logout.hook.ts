import { useNavigate } from '@tanstack/react-router';
import {useLogoutMutation} from './logout.mutation';
import { toast } from "sonner";

export const useLogout = () => {
  const {mutate} = useLogoutMutation();
  const navigate = useNavigate();

  const logout = () => {
    mutate(undefined, {
      onError: (error) => {
        console.log('onError', error);

        toast.error(`Fail: ${error.message}`, {
          style: {
            backgroundColor: "#f44336",
            color: "#fff",
          },
        });
      },
      onSuccess: async () => {
        toast.success('Logged out',
          {style: {
            backgroundColor: "#4caf50",
            color: "#fff",
          },
          }
        );

        await navigate({
          to: '/',
          replace: true,
        });

      },
    });
  };
  return logout;
}
