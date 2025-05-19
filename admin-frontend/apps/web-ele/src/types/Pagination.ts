export interface Pagination<T> {
  records: T[];
  total: number;
  page: number;
  size: number;
  pages: number;
}
