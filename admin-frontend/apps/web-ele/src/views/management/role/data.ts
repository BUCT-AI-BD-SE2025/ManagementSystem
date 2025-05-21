import type {VbenFormProps} from "#/adapter/form";
import type { VxeTableGridOptions } from "#/adapter/vxe-table";
import type { Artifact } from "#/types/Artifact";

export const useGridSchema = () => {
  const schema: VbenFormProps['schema'] = [
    {
      component: 'Input',
      fieldName: 'roleId',
      label: 'ID',
    },
    {
      component: 'Input',
      fieldName: 'roleName',
      label: '名称',
    },
    {
      component: 'Input',
      fieldName: 'roleCode',
      label: '权限码',
    },
    {
      component: 'Input',
      fieldName: 'description',
      label: '描述',
    },
  ];
  return schema;
}
export const useFormSchema = () => {
  const schema: VbenFormProps['schema'] = [
    {
      component: 'Input',
      fieldName: 'roleId',
      label: 'ID',
    },
    {
      component: 'Input',
      fieldName: 'roleName',
      label: '名称',
    },
    {
      component: 'Input',
      fieldName: 'roleCode',
      label: '权限码',
    },
    {
      component: 'Input',
      fieldName: 'description',
      label: '描述',
    }]
  return schema;
}


export const useColumns = () =>{
  const columns: VxeTableGridOptions<Artifact>['columns'] = [
    { fixed: 'left', align: 'left',title: '',  type: 'checkbox',  width: 50},
    { fixed: 'left',field: 'roleId', title: 'ID' },
    { field: 'roleName', title: '名称'},
    { field: 'roleCode', title: '权限码'},
    { field: 'description', title: '描述'},
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
