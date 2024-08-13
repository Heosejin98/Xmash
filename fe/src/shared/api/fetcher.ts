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
      const data = await fn(...args);
      return {
        isSuccess: true,
        isError: false,
        data,
        error: null
      }
    } catch (error: unknown) {
      console.error(error);
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

    console.log(request);
    //401 id, pw 틀릴때

    const response = await fetch(import.meta.env.VITE_API_URL + url, request);
    return await response.json();
  })

  static get = withAsync(async (url: string, data: Params) => {
    const request = {
      ...this.request,
      method: "GET",
    }
    const queryString = new URLSearchParams(data).toString();

    const response = await fetch(import.meta.env.VITE_API_URL +
      queryString ? `${url}?${queryString}` : url
      , request
    );

    return await response.json();

  });

  static put = withAsync(async (url: string, data: Params) => {
    const request = {
      ...this.request,
      method: "PUT",
      body: JSON.stringify(data),
    }
    const response = await fetch(import.meta.env.VITE_API_URL + url, request);
    return await response.json();
  });

  static delete = withAsync(async (url: string, data: Params) => {
    const queryString = new URLSearchParams(data).toString();

    const request = {
      ...this.request,
      method: "DELETE",
    }

    const response = await fetch(import.meta.env.VITE_API_URL +
      queryString ? `${url}?${queryString}` : url
      , request);
    return await response.json();
  });

  static patch = withAsync(async (url: string, data: Params) => {
    const queryString = new URLSearchParams(data).toString();

    const request = {
      ...this.request,
      method: "PATCH",
    }

    const response = await fetch(import.meta.env.VITE_API_URL +
      queryString ? `${url}?${queryString}` : url
      , request);
    return await response.json();
  });
}
