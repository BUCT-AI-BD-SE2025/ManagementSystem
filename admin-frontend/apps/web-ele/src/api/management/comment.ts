import { requestClient } from '#/api/request';
import type { Pagination} from "#/types/Pagination";
import type {Comment} from "#/types/Comment";

export namespace CommentApi {
  export async function getCommentList(params: {
    page?: number;
    pageSize?: number;
  }) {
    return requestClient.get<Pagination<Comment>>('/comment', {params});
  }


  export async function getCommentByUid(commentId: string) {
    return requestClient.get<Comment>(`/comment/${commentId}`);
  }

  export async function createComment(data: & {
    uid?: string
  }) {
    return requestClient.post('/comment', data);
  }

  export async function updateComment(uid: string, data: Partial<Omit<Comment, 'commentId'>>) {
    return requestClient.put(`/comment/${uid}`, data);
  }

  export async function deleteComment(commentId: string) {
    return requestClient.delete(`/comment/${commentId}`);
  }
  export async function batchDeleteComment(ids: string[]) {
    return requestClient.delete('/comment/batch', {data: ids});
  }
}
