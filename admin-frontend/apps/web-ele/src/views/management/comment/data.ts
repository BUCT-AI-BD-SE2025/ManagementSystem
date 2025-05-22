import type {VbenFormProps} from "#/adapter/form";
import type { VxeTableGridOptions } from "#/adapter/vxe-table";
import type { Comment } from "#/types/Comment";

export const useGridSchema = () => {
  const schema: VbenFormProps['schema'] = [
    {
      component: 'Input',
      fieldName: 'commentId',
      label: 'ID',
    },
    {
      component: 'Input',
      fieldName: 'userId',
      label: '用户名',
    },
    {
      component: 'Input',
      fieldName: 'content',
      label: '内容',
    },
    {
      component: 'Input',
      fieldName: 'status',
      label: ' 状态',
    },
    {
      component: 'DatePicker',
      fieldName: 'createdAt',
      label: '创建时间',
      componentProps: {
        type: 'daterange',
        rangeSeparator: '至',
        startPlaceholder: '开始日期',
        endPlaceholder: '结束日期',
      },
    },
    {
      component: 'DatePicker',
      fieldName: 'updatedAt',
      label: '登录时间',
      componentProps: {
        type: 'daterange',
        rangeSeparator: '至',
        startPlaceholder: '开始日期',
        endPlaceholder: '结束日期',
      },
    },
  ]
  return schema;
}
export const useFormSchema = () => {
  const schema: VbenFormProps['schema'] =
    [
      {
        component: 'Input',
        fieldName: 'userId',
        label: '用户ID',
      },
      {
        component: 'Input',
        fieldName: 'content',
        label: '内容',
      },
      {
        component: 'Input',
        fieldName: 'status',
        label: '状态',
      },
    ]
  return schema;
}


export const useColumns = () =>{
  const columns: VxeTableGridOptions<Comment>['columns'] = [
    { align: 'left', title: '',  type: 'checkbox',  width: 50},
    { field: 'commentId', title: 'ID' },
    { field: 'userId', title: '用户ID' },
    { field: 'content', title: '内容' },
    { field: 'status', title: '状态' },
    { field: 'createdTime', title: '创建时间' },
    { field: 'updatedTime', title: '更新时间' },
    {
      field: 'action',
      fixed: 'right',
      title: '操作',
      width: 120,
      slots: { default: 'action' },
    },
  ]
  return columns;
}
