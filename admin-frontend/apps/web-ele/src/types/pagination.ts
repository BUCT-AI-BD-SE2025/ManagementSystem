export interface Pagination<T> {
  records: T[];
  total: number;
  size: number;
  current: number;
  pages: number;
}
