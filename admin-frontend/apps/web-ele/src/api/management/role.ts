import {requestClient} from "#/api/request";
import type {Role} from "#/types/Role";
import type {Pagination} from "#/types/Pagination";

export namespace RoleApi {
  export async function getRoleList(params: any)
  {
    return requestClient.get<Pagination<Role>>('/roles', {
      params,
    });
  }
  export async function createRole(data: Partial<Role>)
  {
    return requestClient.post<Role>('/roles', data);
  }
  export async function updateRole(roleId: string, data: Partial<Role>)
  {
    return requestClient.put<Role>(`/roles/${roleId}`, data);
  }
  export async function deleteRole(roleId: string)
  {
    return requestClient.delete(`/roles/${roleId}`);
  }
  export async function batchDeleteRole(ids: string[])
  {
    return requestClient.delete('/roles/batch', {data: ids});
  }
}
