import type {VbenFormProps} from "#/adapter/form";
import type { VxeTableGridOptions } from "#/adapter/vxe-table";
import type {OperationLog as DataType} from "#/types/OperationLog";

export const useGridSchema = () => {
  const schema: VbenFormProps['schema'] = [
    {
      component: 'Input',
      fieldName: 'logId',
      label: 'ID',
    },
    {
      component: 'Input',
      fieldName: 'operatorId',
      label: '操作人ID',
    },
    {
      component: 'Input',
      fieldName: 'targetType',
      label: '目标类型',
    },
    {
      component: 'Input',
      fieldName: 'targetId',
      label: '目标ID',
    },
    {
      component: 'Input',
      fieldName: 'actionType',
      label: '操作类型',
    },
    {
      component: 'Input',
      fieldName: 'ipAddress',
      label: 'IP地址',
    },

    {
      component: 'DatePicker',
      fieldName: 'operationTime',
      label: '操作时间',
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
    { field: 'operatorId',  title: '操作人ID' },
    { field: 'targetType',  title: '目标类型' },
    { field: 'targetId',  title: '目标ID' },
    { field: 'actionType',  title: '操作类型' },
    { field: 'ipAddress',  title: 'IP地址' },
    { field: 'logTime',  title: '操作时间' },
  ];
  return columns;
}
