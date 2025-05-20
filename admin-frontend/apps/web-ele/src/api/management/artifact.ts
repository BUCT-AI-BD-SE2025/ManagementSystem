import { requestClient} from "#/api/request";
import type {Pagination} from "#/types/Pagination";
import type {Artifact} from "#/types/Artifact";

export namespace ArtifactApi {
  export async function getArtifactList(params: any) {
    return requestClient.get<Pagination<Artifact>>('/artifacts', {
      params,
    });
  }

  export async function getArtifactById(id: string) {
    return requestClient.get<Artifact>(`/artifacts/${id}`);
  }

  export async function createArtifact(data: Partial<Artifact>) {
    return requestClient.post<Artifact>('/artifacts', data);
  }

  export async function updateArtifact(id: string, data: Partial<Artifact>) {
    return requestClient.put<Artifact>(`/artifacts/${id}`, data);
  }

  export async function deleteArtifact(id: string) {
    return requestClient.delete(`/artifacts/${id}`);
  }

}
