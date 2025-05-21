<script setup lang="ts">

import type { VbenFormProps} from '#/adapter/form';
import {
  type OnActionClickParams,
  useVbenVxeGrid,
  type VxeTableGridOptions
} from "#/adapter/vxe-table";

import {Page, useVbenDrawer} from '@vben/common-ui';

import { ElButton, ElImage } from 'element-plus';

import {UserApi} from "#/api/management/user";
import {useBaseCRUD} from "#/hooks/base/useBaseCRUD";

import From from "./from.vue";
import type {User} from "#/types/User";
import {$t} from "@vben/locales";
import {useColumns, useGridSchema} from "./data";
import {useBaseGridOptions} from "#/hooks/base/useBaseGridOptions";

const [FormDrawer, formDrawerApi] = useVbenDrawer({
  connectedComponent: From,
  destroyOnClose: true,
})

interface RowType extends User{}

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

const gridOptions: VxeTableGridOptions = useBaseGridOptions(useColumns(), UserApi.getUserList(),
  {
    proxyConfig: {
      ajax: {
        query: async ({ page }, formValues) => {
          const params = {
            page: page.currentPage,
            pageSize: page.pageSize,
            ...formValues,
            startTime: formValues.createdAt?.[0],
            endTime: formValues.createdAt?.[1],
            loginTime: formValues.loginAt?.[0],
            loginEndTime: formValues.loginAt?.[1],
          }
          delete params.createdAt;
          delete params.loginAt;

          const res = await UserApi.getUserList(params);
          return {
            items: res.records,
            total: res.total,
          };
        },
      },
    }
  }
);

const [Grid, gridApi] = useVbenVxeGrid({
  formOptions,
  gridOptions,
});

const { handleDelete, handleBatchDelete, handleCreate, handleEdit } = useBaseCRUD({
  api: {
    deleteItem: UserApi.deleteUser,
    batchDeleteItems: UserApi.batchDeleteUser,
  },
  gridApi: gridApi,
  formDrawerApi,
})

async function handleBatchDelete() {
  const selectedRows = await gridApi.grid.getCheckboxRecords() as Array<T & { id: string | number }>

  if (!selectedRows.length) {
    ElMessage.warning('请至少选择一条记录')
    return
  }

  try {
    await ElMessageBox.confirm(`确定要删除选中的 ${selectedRows.length} 条记录？`, '提示', {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning',
    })
    const ids = selectedRows.map(row => row.id)
    const res = await api.batchDeleteItems(ids)

    if (res) {
      ElMessage.success('批量删除成功')
      onRefresh?.()
    } else {
      ElMessage.error(res.message || '批量删除失败')
    }
  } catch (error) {
    ElMessage.warning('操作已取消')
  }
}

function onActionClick(e: OnActionClickParams<RowType>) {
  const { code, row } = e;

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

