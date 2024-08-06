import { FormSchema } from "@/entities/login";

export const login = async (data: FormSchema) => {
  console.log('login api call')
  console.log(data);

  // TODO - set the user in local storage ??
}
