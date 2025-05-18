import type { Artifact } from '@vben/types';

import { requestClient } from '#/api/request';

/**
 * 获取文物列表
 */
export async function getArtifactListApi(params: Record<string, any>) {
  return requestClient.get<Artifact[]>('/artifact/list', { params });
}

/**
 * 获取单个文物信息
 */
export async function getArtifactDetailApi(id: number) {
  return requestClient.get<Artifact>(`/artifact/detail/${id}`);
}

/**
 * 创建文物
 */
export async function createArtifactApi(data: Partial<Artifact>) {
  return requestClient.post('/artifact/create', data);
}

/**
 * 更新文物
 */
export async function updateArtifactApi(id: number, data: Partial<Artifact>) {
  return requestClient.put(`/artifact/update/${id}`, data);
}

/**
 * 删除文物
 */
export async function deleteArtifactApi(id: number) {
  return requestClient.delete(`/artifact/delete/${id}`);
}
