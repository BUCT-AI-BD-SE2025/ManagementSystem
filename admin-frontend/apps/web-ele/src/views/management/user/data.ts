import type {VbenFormProps} from "#/adapter/form";
import type { VxeTableGridOptions } from "#/adapter/vxe-table";
import type { Artifact } from "#/types/Artifact";

export const useGridSchema = () => {
  const schema: VbenFormProps['schema'] = [
    {
      component: 'Input',
      fieldName: 'uid',
      label: 'ID',
    },
    {
      component: 'Input',
      fieldName: 'username',
      label: '用户名',
    },
    {
      component: 'Input',
      fieldName: 'email',
      label: '邮箱',
    },
    {
      component: 'Input',
      fieldName: 'phone',
      label: '手机号',
    },
    {
      component: 'Select',
      fieldName: 'sex',
      label: '性别',
      componentProps: {
        options: [
          { label: '全部', value: '' },
          { label: '男', value: '0' },
          { label: '女', value: '1' },
          { label: '未知', value: '2' },
        ],
      },
    },
    {
      component: 'Select',
      fieldName: 'status',
      label: '状态',
      componentProps: {
        options: [
          { label: '全部', value: '' },
          { label: '激活', value: 'ACTIVE' },
          { label: '锁定', value: 'LOCKED' },
          { label: '删除', value: 'DELETED' },
        ],
      },
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
      fieldName: 'loginAt',
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
        fieldName: 'uid',
        label: 'ID',
      },
      {
      component: 'Input',
      fieldName: 'username',
      label: '用户名',
    },
    {
      component: 'Input',
      fieldName: 'nickname',
      label: '昵称',
    },
      {
      component: 'Input',
      fieldName: 'avatarUrl',
      label: '头像Url',
    },
    {
      component: 'Input',
      fieldName: 'password',
      label: '密码',
      componentProps: {
        type: 'password',
        showPassword: true,
      },
    },
    {
      component: 'Input',
      fieldName: 'email',
      label: '电子邮箱',
    },
    {
      component: 'Input',
      fieldName: 'phone',
      label: '手机号',
    },
    {
      component: 'Select',
      fieldName: 'sex',
      label: '性别',
      componentProps: {
        options: [
          { label: '男', value: '0' },
          { label: '女', value: '1' },
          { label: '未知', value: '2' },
        ],
      },
    },
    {
      component: 'Select',
      fieldName: 'status',
      label: '状态',
      componentProps: {
        options: [
          { label: '激活', value: 'ACTIVE' },
          { label: '锁定', value: 'LOCKED' },
          { label: '删除', value: 'DELETED'},
        ],
      },
    }
  ]
  return schema;
}


export const useColumns = () =>{
  const columns: VxeTableGridOptions<Artifact>['columns'] = [
    { align: 'left', title: '',  type: 'checkbox',  width: 50},
    { field: 'uid', title: 'ID', width: 50 },
    { field: 'username', title: '用户名', width: 100 },
    { field: 'nickname', title: '昵称', width: 100 },
    { field: 'email', title: '电子邮箱', width: 150 },
    { field: 'phone', title: '手机号',width: 150 },
    { field: 'sex', title: '性别',width: 50,
      formatter: ({ cellValue }) =>
        cellValue === '0' ? '男' :
          cellValue === '1' ? '女' : '未知',
    },
    { field: 'ip', title: 'IP',width: 150 },
    { field: 'avatarUrl', title: '头像', width: 150, slots:{default:'image-url'}},
    { field: 'status', title: '状态', width: 100 },
    { field: 'lastLogin', title: '最后登录', width: 150 },
    { field: 'createdAt', title: '创建时间', width: 150 },
    { field: 'updatedAt', title: '更新时间', width: 150 },
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
