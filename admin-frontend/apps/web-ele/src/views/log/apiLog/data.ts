import type {VbenFormProps} from "#/adapter/form";
import type { VxeTableGridOptions } from "#/adapter/vxe-table";
import type {LoginLog as DataType} from "#/types/LoginLog";

export const useGridSchema = () => {
  const schema: VbenFormProps['schema'] = [
    {
      component: 'Input',
      fieldName: 'logId',
      label: 'ID',
    },
    {
      component: 'Input',
      fieldName: 'userId',
      label: '用户ID',
    },
    {
      component: 'Input',
      fieldName: 'username',
      label: '用户名',
    },
    {
      component: 'Input',
      fieldName: 'ipAddress',
      label: 'IP地址',
    },
    {
      component: 'Input',
      fieldName: 'deviceInfo',
      label: '设备信息',
    },
    {
      component: 'Input',
      fieldName: 'isSuccess',
      label: '是否成功',
    },
    {
      component: 'DatePicker',
      fieldName: 'loginTime',
      label: '登陆时间',
      componentProps: {
        type: 'daterange',
        rangeSeparator: '至',
        startPlaceholder: '开始日期',
        endPlaceholder: '结束日期',
      },
    }]

  return schema;
}

export const useColumns = () =>{
  const columns: VxeTableGridOptions<DataType>['columns'] = [
    { fixed: 'left', align: 'left',title: '',  type: 'checkbox',  width: 50},
    { fixed: 'left',field: 'logId', title: 'ID' },
    { field: 'userId', title: '用户ID'},
    { field: 'username', title: '用户名'},
    { field: 'ipAddress', title: 'IP地址'},
    { field: 'deviceInfo', title: '设备信息'},
    { field: 'isSuccess', title: '是否成功'},
    { field: 'loginTime', title: '登录时间'},
  ];
  return columns;
}
