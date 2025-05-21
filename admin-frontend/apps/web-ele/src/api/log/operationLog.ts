import {requestClient} from "#/api/request";
import type {Pagination} from "#/types/Pagination";
import type {OperationLog} from "#/types/OperationLog";

export namespace OperationLogApi {
  export async function getOperationLogList(params: any){
    return requestClient.get<Pagination<OperationLog>>('/log/operation', {
      params,
    });
  }
}
