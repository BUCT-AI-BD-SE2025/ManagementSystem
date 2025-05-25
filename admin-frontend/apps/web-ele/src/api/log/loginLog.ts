import {requestClient} from "#/api/request";
import type {Pagination} from "#/types/Pagination";
import type {LoginLog} from "#/types/LoginLog";

export namespace LoginLogApi {
  export async function getLoginLogList(params: any)  {
    return requestClient.get<Pagination<LoginLog>>('/log/login', {
      params,
    });
  }
}
