import type { Artifact } from '@vben/types';

import type { Pagination } from '#/types/pagination';
import type { ApiResponse } from '#/utils/response';

import { requestClient } from '#/api/request';

/**
 * 获取文物列表
 */
export async function getArtifactListApi(params: Record<string, any>) {
  return requestClient.get<ApiResponse<Pagination<Artifact>>>('/artifacts', {
    params,
  });
}
