<script setup lang="ts">

import type { VbenFormProps} from '#/adapter/form';
import {
  type OnActionClickParams,
  useVbenVxeGrid,
  type VxeTableGridOptions
} from "#/adapter/vxe-table";

import {Page, useVbenDrawer} from '@vben/common-ui';

import {ElButton, ElImage, ElMessage} from 'element-plus';

import {CommentApi} from "#/api/management/comment";
import ExtraModal from './modal.vue';
import { useVbenModal } from '@vben/common-ui';
const [Modal, modalApi] = useVbenModal({
  // 连接抽离的组件
  connectedComponent: ExtraModal,
});

import Form from "./form.vue";
import type {Comment} from "#/types/Comment";
import {$t} from "@vben/locales";
import {useColumns, useGridSchema} from "./data";
import {useBaseGridOptions} from "#/hooks/base/useBaseGridOptions";



interface RowType extends Comment{
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

const gridOptions: VxeTableGridOptions = useBaseGridOptions(useColumns(), CommentApi.getCommentList,
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
          delete params.updatedAt;

          const res = await CommentApi.getCommentList(params);
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

async function openModal(ids: string[], status: string) {
  modalApi
    .setData({
      ids: ids,
      status: status,
      gridApi: gridApi,
    })
    .open();
}

async function batchOperate(status: string) {
  const selectedRows = await gridApi.grid.getCheckboxRecords()

  if (!selectedRows.length) {
    ElMessage.warning('请至少选择一条记录')
    return
  }
  const ids = selectedRows.map(item => item.commentId)
  await openModal(ids,status)
}


async function onActionClick(e: OnActionClickParams<RowType>) {
  const { code, row } = e;
  row.id = row.commentId;
  let ids;
  ids = [row.id]
  switch (code) {
    case 'pass':
      await openModal(ids, 'Pass')
      break;
    case 'block':
      await openModal(ids, 'Block')
      break;
    case 'detail':
      formDrawerApi.setData(row)
      formDrawerApi.open();
      break;
    default:
      console.warn(`未知的操作类型: ${code}`);
  }
}

</script>

<template>
  <Page auto-content-height>
    <Modal />
    <FormDrawer />
    <Grid>
      <template #toolbar-tools>
        <ElButton type="primary" @click="batchOperate('Pass')">
          {{ $t('page.review.pass') }}
        </ElButton>
        <ElButton type="danger" @click="batchOperate('Block')">
          {{ $t('page.review.block') }}
        </ElButton>
      </template>
      <template #action="{ row }">
        <ElButton
          v-for="action in ['pass', 'block','detail' ]"
          :key="action"
          :type="action === 'block' ? 'danger' : 'primary'"
          @click="onActionClick({ code: action, row })"
          size="small"
        >
          {{ $t(`page.review.${action}`) }}
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

