import { requestClient} from "#/api/request";
import type {Pagination} from "#/types/Pagination";

export namespace ArtifactApi {
  /** 文物接口参数 */
  export interface Artifact {
    id: number;
    originId: string;
    title: string;
    url: string;
    image: string;
    location: string;
    period: string;
    material: string;
    measurement: string;
    artist: string;
    creditLine: string;
    type: string;
    description: string;
    illusion: string;
  }

  export async function getArtifactList(params: any) {
    return requestClient.get<Pagination<Artifact>>('/artifacts', {
      params,
    });
  }

}
