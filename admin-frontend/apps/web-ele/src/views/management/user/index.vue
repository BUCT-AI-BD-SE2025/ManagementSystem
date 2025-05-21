<script setup lang="ts">

import type { VbenFormProps} from '#/adapter/form';
import {
  type OnActionClickParams,
  useVbenVxeGrid,
  type VxeTableGridOptions
} from "#/adapter/vxe-table";

import {Page, useVbenDrawer} from '@vben/common-ui';

import {ElButton, ElImage} from 'element-plus';

import {UserApi} from "#/api/management/user";
import {useBaseCRUD} from "#/hooks/base/useBaseCRUD";

import Form from "./form.vue";
import type {User} from "#/types/User";
import {$t} from "@vben/locales";
import {useColumns, useGridSchema} from "./data";
import {useBaseGridOptions} from "#/hooks/base/useBaseGridOptions";



interface RowType extends User{
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

const gridOptions: VxeTableGridOptions = useBaseGridOptions(useColumns(), UserApi.getUserList,
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

const [FormDrawer, formDrawerApi] = useVbenDrawer({
  connectedComponent: Form,
  destroyOnClose: true,
  onClosed: async () => {await gridApi.query()},
})
const { handleDelete, handleBatchDelete, handleCreate, handleEdit } = useBaseCRUD<User, string>({
  api: {
    deleteItem: UserApi.deleteUser,
    batchDeleteItems: UserApi.batchDeleteUser,
  },
  gridApi: gridApi,
  formDrawerApi,
  idField: 'uid'
})

function onActionClick(e: OnActionClickParams<RowType>) {
  const { code, row } = e;
  row.id = row.uid;
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
        <ElImage :src="row.avatarUrl" height="30px" width="30px" lazy/>
      </template>
    </Grid>
  </Page>
</template>

<style scoped>

</style>

