import type {VbenFormProps} from "#/adapter/form";
import type { VxeTableGridOptions } from "#/adapter/vxe-table";
import type {ReviewLog as DataType} from "#/types/ReviewLog";

export const useGridSchema = () => {
  const schema: VbenFormProps['schema'] = [
    {
      component: 'Input',
      fieldName: 'reviewedId',
      label: 'ID',
    },
    {
      component: 'Input',
      fieldName: 'commentId',
      label: '评论ID',
    },
    {
      component: 'Input',
      fieldName: 'reviewerId',
      label: '审核人ID',
    },
    {
      component: 'Status',
      fieldName: 'reviewResult',
      label: '审核结果',
    },
    {
      component: 'Input',
      fieldName: 'blockReason',
      label: '驳回理由',
    }]

  return schema;
}

export const useColumns = () =>{
  const columns: VxeTableGridOptions<DataType>['columns'] = [
    { fixed: 'left', align: 'left',title: '',  type: 'checkbox',  width: 50},
    { field: 'reviewId', title: '审核ID', width: 120,},
    { field: 'commentId', title: '评论ID', width: 120,},
    { field: 'userId', title: '用户ID', width: 120,},
    { field: 'status', title: '状态', width: 120,},
    { field: 'reviewResult', title: '审核结果', width: 120,},
    { field: 'blockReason', title: '驳回理由', width: 120,},
    { field: 'reviewTime', title: '审核时间', width: 120,},
  ];
  return columns;
}
