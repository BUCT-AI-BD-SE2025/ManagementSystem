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
// #/types/artifact.ts

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

/**
 * 用于接口返回的数据结构，字段允许为 null
 */
export interface ArtifactItem {
  id: number;
  originId: string;
  title: string;
  url: string;
  image: string;
  location: null | string;
  period: null | string;
  material: null | string;
  measurement: null | string;
  artist: null | string;
  creditLine: null | string;
}
