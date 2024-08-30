interface Params {
  [key: string]: string;
}

export interface Result<T> {
  isSuccess: boolean;
  isError: boolean;
  data: Awaited<T> | null;
  error: Error | null;
}

const withAsync = <Fn extends (...args: never[]) => Promise<any>, R extends ReturnType<Fn>>(fn: Fn) => {
  return async (...args: Parameters<Fn>): Promise<Result<R>> => {
    try {
      const data: Response = await fn(...args);
      if (!data.ok) {
        throw data;
      }

      return {
        isSuccess: true,
        isError: false,
        data: await data.json(),
        error: null
      }
    } catch (error: unknown) {
      if (error instanceof Response) {
        if (error.status === 401) {
          return {
            isSuccess: false,
            isError: true,
            data: null,
            error: new Error("Unauthorized")
          }
        }
        if (error.status === 404) {
          return {
            isSuccess: false,
            isError: true,
            data: null,
            error: new Error("Not Found")
          }
        }
        if (error.status === 500) {
          return {
            isSuccess: false,
            isError: true,
            data: null,
            error: new Error("Internal Server Error")
          }
        }
      }

      return {
        isSuccess: false,
        isError: true,
        data: null,
        error: error as Error
      }
    }
  };
}

export class Fetcher {
  private static request: RequestInit = {
    mode: 'cors',
    credentials: 'same-origin',
    keepalive: false,
    headers: {
      "Content-Type": "application/json",
      "Access-Control-Allow-Origin": "*",
    },
  };

  static post = withAsync(async (url: string, data: Params) => {
    const request = {
      ...this.request,
      method: "POST",
      body: JSON.stringify(data),
    }

    return fetch(import.meta.env.VITE_API_URL + url, request);
  })

  static get = withAsync(async (url: string, data: Params) => {
    const request = {
      ...this.request,
      method: "GET",
    }
    const queryString = new URLSearchParams(data).toString();

    return fetch(import.meta.env.VITE_API_URL +
      queryString ? `${url}?${queryString}` : url
      , request
    );


  });

  static put = withAsync(async (url: string, data: Params) => {
    const request = {
      ...this.request,
      method: "PUT",
      body: JSON.stringify(data),
    }
    return fetch(import.meta.env.VITE_API_URL + url, request);
  });

  static delete = withAsync(async (url: string, data: Params) => {
    const queryString = new URLSearchParams(data).toString();

    const request = {
      ...this.request,
      method: "DELETE",
    }

    return fetch(import.meta.env.VITE_API_URL +
      queryString ? `${url}?${queryString}` : url
      , request);
  });

  static patch = withAsync(async (url: string, data: Params) => {
    const queryString = new URLSearchParams(data).toString();

    const request = {
      ...this.request,
      method: "PATCH",
    }

    return fetch(import.meta.env.VITE_API_URL +
      queryString ? `${url}?${queryString}` : url
      , request);
  });
}
