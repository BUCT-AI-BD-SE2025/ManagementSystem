import { requestClient } from '#/api/request';
import type { Pagination} from "#/types/Pagination";
import type {User} from "#/types/User";

export namespace UserApi {
  /**
   * 获取用户分页列表
   * @param params 查询参数
   */
  export async function getUserList(params: {
    page?: number;
    pageSize?: number;
    username?: string;
    email?: string;
  }) {
    return requestClient.get<Pagination<User>>('/user/all', {params});
  }

  /**
   * 根据 UID 获取用户详情
   * @param uid 用户ID
   */
  export async function getUserByUid(uid: string) {
    return requestClient.get<User>(`/user/${uid}`);
  }

  /**
   * 创建新用户
   * @param data 用户数据
   */
  export async function createUser(data: & {
    uid?: string
  }) {
    return requestClient.post('/user', data);
  }

  /**
   * 更新用户信息
   * @param uid 用户ID
   * @param data 更新数据
   */
  export async function updateUser(uid: string, data: Partial<Omit<User, 'uid'>>) {
    return requestClient.put(`/user/${uid}`, data);
  }

  /**
   * 删除用户
   * @param uid 用户ID
   */
  export async function deleteUser(uid: string) {
    return requestClient.delete(`/user/${uid}`);
  }
  export async function batchDeleteUser(uids: string[]) {
    return requestClient.delete('/user/batch', {data: uids});
  }

  export async function getUserRoles(roleId: string) {
    return requestClient.get(`/user/${roleId}/roles`);
  }
  export async function assignRoles(userId: string, rolesIds: string[]) {
    return requestClient.post(`/user/${userId}/roles`, rolesIds);
  }
}
