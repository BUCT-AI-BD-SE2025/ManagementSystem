import type {VxeTableGridOptions} from "@vben/plugins/vxe-table";
export function useBaseGridOptions<T>(
  columns: any,
  getList: (params: any) => Promise<any>,
  options?: VxeTableGridOptions<T>
  ){
    const defaultOptions: VxeTableGridOptions<T> = {
      checkboxConfig: {
        highlight: true,
        labelField: 'name',
      },
      columns: columns,
      exportConfig: {},
      height: 'auto',
      keepSource: true,
      pagerConfig: {},
      proxyConfig: {
        ajax: {
          query: async ({page}, formValues) => {
            const res = await getList({
              page: page.currentPage,
              pageSize: page.pageSize,
              ...formValues,
            });
            return {
              items: res.records,
              total: res.total
            }
          },
        },
      },
      toolbarConfig: {
        custom: true,
        export: true,
        refresh: true,
        resizable: true,
        search: true,
        zoom: true,
      },
    }
  return mergeDeep<VxeTableGridOptions<T>>({},defaultOptions, options ?? {})
}
function isObject(obj: any): boolean {
  return obj && typeof obj === 'object' && !Array.isArray(obj);
}
// 修改 mergeDeep 函数如下：
function mergeDeep<T>(target: T, ...sources: Partial<T>[]): T {
  if (!sources.length) return target;

  const source = sources.shift();

  if (isObject(target) && isObject(source)) {
    for (const key in source) {
      const targetValue = target[key];
      const sourceValue = source[key];

      if (isObject(targetValue) && isObject(sourceValue)) {
        // 强制转为 any 类型处理深层嵌套
        mergeDeep(targetValue as any, sourceValue as any);
      } else {
        target[key] = sourceValue ?? targetValue;
      }
    }
  }

  return mergeDeep(target, ...sources);
}
