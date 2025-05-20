<script setup lang="ts">

import type { VbenFormProps } from '#/adapter/form';
import {useVbenVxeGrid, type VxeTableGridOptions} from "#/adapter/vxe-table";

import { Page, useVbenDrawer } from '@vben/common-ui';
import { ElButton, ElMessage, ElMessageBox, ElImage } from 'element-plus';

import type  { OnActionClickParams} from "#/adapter/vxe-table";
import {ArtifactApi} from "#/api/management/artifact";
import getArtifactList = ArtifactApi.getArtifactList;

import ArtifactForm from "#/views/management/artifact/artifactForm.vue";
import type {Artifact as DataType} from "#/types/Artifact";
import {$t} from "@vben/locales";

import { useColumns } from "./columns";
import {useSchema} from "./schema";

const [FormDrawer, formDrawerApi] = useVbenDrawer({
  connectedComponent: ArtifactForm,
  destroyOnClose: true,
})

interface RowType extends DataType{}

function onActionClick(e: OnActionClickParams<RowType>) {
  const { code, row } = e;

  switch (code) {
    case 'edit':
      onEdit(row);
      break;
    case 'delete':
      onDelete(row);
      break;
    default:
      console.warn(`未知的操作类型: ${code}`);
  }
}

function onEdit(row: RowType) {
  formDrawerApi.setData(row).open()
  onRefresh();
}

async function onDelete(row: RowType) {
  try {
    await ElMessageBox.confirm('确定要删除该文物吗？', '提示', {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning',
    });

    const res = await ArtifactApi.deleteArtifact(row.id.toString());
    if (res) {
      ElMessage.success('删除成功');
      onRefresh();
    } else {
      ElMessage.error(res.message || '删除失败');
    }
  } catch (error) {
    ElMessage.warning('操作已取消');
  }
}

async function onBatchDelete() {
  const selectedRows = await gridApi.grid.getCheckboxRecords();
  if (!selectedRows.length) {
    ElMessage.warning('请至少选择一条记录');
    return;
  }

  try {
    await ElMessageBox.confirm(
      `确定要删除选中的 ${selectedRows.length} 记录？`,
      '提示',
      {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning',
      }
    );

    // 提取所有 id
    const ids = selectedRows.map(row => row.id);

    // 发送请求
    const res = await ArtifactApi.batchDeleteArtifact(ids);

    if (res) {
      ElMessage.success('批量删除成功');
      onRefresh();
    } else {
      ElMessage.error(res.message || '批量删除失败');
    }
  } catch (error) {
    ElMessage.warning('操作已取消');
  }
}

function onRefresh() {
  gridApi.query();
}


function onCreate() {
  formDrawerApi.setData({}).open();
  onRefresh();
}

const schema = useSchema();
const formOptions: VbenFormProps = {
  // 默认展开
  collapsed: false,
  schema,
  // 控制表单是否显示折叠按钮
  showCollapseButton: true,
  // 是否在字段值改变时提交表单
  submitOnChange: true,
  // 按下回车时是否提交表单
  submitOnEnter: false,
};

const columns = useColumns();
const gridOptions: VxeTableGridOptions<RowType> = {
  checkboxConfig: {
    highlight: true,
    labelField: 'name',
  },
  columns,
  exportConfig: {},
  height: 'auto',
  keepSource: true,
  pagerConfig: {},
  proxyConfig: {
    ajax: {
      query: async ({page}, formValues) => {
        const res = await getArtifactList({
          page: page.currentPage,
          pageSize: page.pageSize,
          ...formValues,
        });

        return {
          items: res.records,
          total: res.total
        }
      },
    },
  },
  toolbarConfig: {
    custom: true,
    export: true,
    refresh: true,
    resizable: true,
    search: true,
    zoom: true,
  },
};

const [Grid, gridApi] = useVbenVxeGrid({
  formOptions,
  gridOptions,
});


</script>

<template>
  <Page auto-content-height>
    <FormDrawer />
    <Grid>
      <template #toolbar-tools>
        <ElButton type="primary" @click="onCreate">
          {{ $t('common.create') }}
        </ElButton>
        <ElButton type="danger" @click="onBatchDelete">
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

