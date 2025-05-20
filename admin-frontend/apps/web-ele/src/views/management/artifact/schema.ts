import type {VbenFormProps} from "#/adapter/form";

export const useSchema = () => {
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
