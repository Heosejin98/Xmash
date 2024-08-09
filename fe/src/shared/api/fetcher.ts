interface Params {
  [key: string]: string;
}

export interface Result<T> {
  isSuccess: boolean;
  isError: boolean;
  data: Awaited<T> | null;
  error: Error | null;
}

const withAsync = <Fn extends (...args: never[]) => Promise<any>, R extends ReturnType<Fn>>(fn: Fn)=> {
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
  private static request:RequestInit = {
    credentials: 'same-origin',
    headers: {
      "Content-Type": "application/json",
    },
  };


  static post = withAsync(async (url: string, data: Params) => {
    const request = {
      ...this.request,
      method: "POST",
      body: JSON.stringify(data),
    }

    const response = await fetch(url, request);
    return response.json();
  })

  static get = withAsync(async (url: string, data: Params) => {
    const request = {
      ...this.request,
      method: "GET",
    }
    const queryString = new URLSearchParams(data).toString();

    const response = await fetch(
      queryString ? `${url}?${queryString}` : url
      , request
    );

    return response.json();

  });

  static put = withAsync(async (url: string, data: Params) => {
    const request = {
      ...this.request,
      method: "PUT",
      body: JSON.stringify(data),
    }
    const response = await fetch(url, request);
    return response.json();
  });

  static delete = withAsync(async (url: string, data: Params) => {
    const queryString = new URLSearchParams(data).toString();

    const request = {
      ...this.request,
      method: "DELETE",
    }

    const response = await fetch(
      queryString ? `${url}?${queryString}` : url
      , request);
    return response.json();
  });

  static patch = withAsync(async (url: string, data: Params) => {
    const queryString = new URLSearchParams(data).toString();

    const request = {
      ...this.request,
      method: "PATCH",
    }

    const response = await fetch(
      queryString ? `${url}?${queryString}` : url
      , request);
    return response.json();
  });


}
