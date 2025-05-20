import type { VxeTableGridOptions } from "#/adapter/vxe-table";
import type { Artifact } from "#/types/Artifact";

export const useColumns = () =>{
  const columns: VxeTableGridOptions<Artifact>['columns'] = [
    { fixed: 'left', align: 'left',title: '',  type: 'checkbox',  width: 50},
    { fixed: 'left',field: 'id', title: 'ID' },
    { field: 'originId', title: '原始ID' },
    { field: 'title', title: '名称' },
    { field: 'url', title: '链接' },
    {
      field: 'image',
      slots: { default: 'image-url',},
      title: '图片',
    },
    { field: 'location', title: '位置' },
    { field: 'period', title: '年代' },
    { field: 'material', title: '材质' },
    { field: 'measurement', title: '尺寸' },
    { field: 'artist', title: '作者' },
    { field: 'creditLine', title: '来源' },
    { field: 'type', title: '类型' },
    { field: 'description', title: '描述' },
    { field: 'illusion', title: '注释' },
    {
      field: 'action',
      fixed: 'right',
      title: '操作',
      width: 120,
      slots: { default: 'action' },
    },
  ];
  return columns;
}
