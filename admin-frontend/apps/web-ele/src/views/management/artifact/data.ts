import type {VbenFormProps} from "#/adapter/form";
import type { VxeTableGridOptions } from "#/adapter/vxe-table";
import type { Artifact } from "#/types/Artifact";

export const useGridSchema = () => {
  const schema: VbenFormProps['schema'] = [
    {
      component: 'Input',
      fieldName: 'id',
      label: 'ID',
    },
    {
      component: 'Input',
      fieldName: 'originId',
      label: '原始ID',
    },
    {
      component: 'Input',
      fieldName: 'title',
      label: '名称',
    },
    {
      component: 'Input',
      fieldName: 'location',
      label: '位置',
    },
    {
      component: 'Input',
      fieldName: 'period',
      label: '年代',
    },
    {
      component: 'Input',
      fieldName: 'material',
      label: '材质',
    },
    {
      component: 'Input',
      fieldName: 'measurement',
      label: '尺寸',
    },
    {
      component: 'Input',
      fieldName: 'artist',
      label: '作者',
    },
    {
      component: 'Input',
      fieldName: 'creditLine',
      label: '来源',
    }
    ,
    {
      component: 'Input',
      fieldName: 'type',
      label: '类型',
    },
    {
      component: 'Input',
      fieldName: 'description',
      label: '描述',
    },
    {
      component: 'Input',
      fieldName: 'illusion',
      label: '注释',
    },
  ];
  return schema;
}
export const useFormSchema = () => {
  const schema: VbenFormProps['schema'] = [
    {
      component: 'Input',
      fieldName: 'originId',
      label: '原始ID',
    },
    {
      component: 'Input',
      fieldName: 'title',
      label: '名称',
    },
    {
      component: 'Input',
      fieldName: 'url',
      label: '链接',
    },
    {
      component: 'Input',
      fieldName: 'image',
      label: '图片',
    },
    {
      component: 'Input',
      fieldName: 'location',
      label: '位置',
    },
    {
      component: 'Input',
      fieldName: 'period',
      label: '年代',
    },
    {
      component: 'Input',
      fieldName: 'material',
      label: '材质',
    },
    {
      component: 'Input',
      fieldName: 'measurement',
      label: '尺寸',
    },
    {
      component: 'Input',
      fieldName: 'artist',
      label: '作者',
    },
    {
      component: 'Input',
      fieldName: 'creditLine',
      label: '来源',
    },
    {
      component: 'Input',
      fieldName: 'type',
      label: '类型',
    },
    {
      component: 'Input',
      fieldName: 'description',
      label: '描述',
    },
    {
      component: 'Input',
      fieldName: 'illusion',
      label: '注释',
    }]
  return schema;
}


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
