<script setup lang="ts">

import type { VbenFormProps } from '#/adapter/form';
import {useVbenVxeGrid, type VxeTableGridOptions} from "#/adapter/vxe-table";

import { Page, useVbenDrawer } from '@vben/common-ui';
import { ElButton, ElImage } from 'element-plus';

import type  { OnActionClickParams} from "#/adapter/vxe-table";
import {RoleApi} from "#/api/management/role";
import getArtifactList = RoleApi.getRoleList;

import Form from "./form.vue";
import type { Role as DataType } from '#/types/Role';
import {$t} from "@vben/locales";

import { useColumns } from "./data";
import {useGridSchema} from "./data";
import {useBaseGridOptions} from "#/hooks/base/useBaseGridOptions";
import {useBaseCRUD} from "#/hooks/base/useBaseCRUD";



interface RowType extends DataType{
  id: string;
}



const formOptions: VbenFormProps = {
  // 默认展开
  collapsed: false,
  schema: useGridSchema(),
  // 控制表单是否显示折叠按钮
  showCollapseButton: true,
  // 是否在字段值改变时提交表单
  submitOnChange: true,
  // 按下回车时是否提交表单
  submitOnEnter: false,
};
const gridOptions: VxeTableGridOptions = useBaseGridOptions(useColumns(), getArtifactList);

const [Grid, gridApi] = useVbenVxeGrid({
  formOptions,
  gridOptions,
});
const [FormDrawer, formDrawerApi] = useVbenDrawer({
  connectedComponent: Form,
  destroyOnClose: true,
  onClosed: async () => {await gridApi.query()},
})
const { handleDelete, handleBatchDelete, handleCreate, handleEdit } = useBaseCRUD({
  api: {
    deleteItem: RoleApi.deleteRole,
    batchDeleteItems: RoleApi.batchDeleteRole,
  },
  gridApi: gridApi,
  formDrawerApi,
  idField: 'roleId',
})



function onActionClick(e: OnActionClickParams<RowType>) {
  const { code, row } = e;
  row.id = row.roleId;
  switch (code) {
    case 'edit':
      handleEdit(row)
      break;
    case 'delete':
      handleDelete(row)
      break;
    default:
      console.warn(`未知的操作类型: ${code}`);
  }
}
</script>

<template>
  <Page auto-content-height>
    <FormDrawer />
    <Grid>
      <template #toolbar-tools>
        <ElButton type="primary" @click="handleCreate">
          {{ $t('common.create') }}
        </ElButton>
        <ElButton type="danger" @click="handleBatchDelete">
          {{ $t('common.delete') }}
        </ElButton>
      </template>
      <template #action="{ row }">
        <ElButton
          v-for="action in ['edit', 'delete']"
          :key="action"
          :type="action === 'delete' ? 'danger' : 'primary'"
          @click="onActionClick({ code: action, row })"
          size="small"
        >
          {{ $t(`common.${action}`) }}
        </ElButton>
      </template>
      <template #image-url="{ row }">
        <ElImage :src="row.image" height="30px" width="30px" lazy/>
      </template>
    </Grid>
  </Page>
</template>

<style scoped>

</style>

