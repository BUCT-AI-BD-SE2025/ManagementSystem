import {requestClient} from "#/api/request";
import type {Permission} from "#/types/Permission";
import type {Pagination} from "#/types/Pagination";

export namespace PermissionApi {
  export async function getPermissionList(params: any)
  {
    return requestClient.get<Pagination<Permission>>('/permissions', {
      params,
    });
  }
  export async function createPermission(data: Partial<Permission>)
  {
    return requestClient.post<Permission>('/permissions', data);
  }
  export async function updatePermission(permId: string, data: Partial<Permission>)
  {
    return requestClient.put<Permission>(`/permissions/${permId}`, data);
  }
  export async function deletePermission(permId: string)
  {
    return requestClient.delete(`/permissions/${permId}`);
  }
  export async function batchDeletePermission(ids: string[])
  {
    return requestClient.delete('/permissions/batch', {data: ids});
  }
}
