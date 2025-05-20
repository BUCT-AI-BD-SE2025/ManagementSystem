<script setup lang="ts">

import type { VbenFormProps} from '#/adapter/form';
import {useVbenVxeGrid, type VxeTableGridOptions} from "#/adapter/vxe-table";

import { Page, useVbenDrawer } from '@vben/common-ui';

import { ElButton, ElMessage, ElMessageBox } from 'element-plus';

import { h } from 'vue'

import {ArtifactApi} from "#/api/management/artifact";
import getArtifactList = ArtifactApi.getArtifactList;

import ArtifactForm from "#/views/management/artifact/artifactForm.vue";
import type {Artifact} from "#/types/Artifact";

const [FormDrawer, formDrawerApi] = useVbenDrawer({
  connectedComponent: ArtifactForm,
  destroyOnClose: true,
})

interface RowType extends Artifact{}

function onEdit(row: RowType) {
  formDrawerApi.setData(row).open()
}

async function onDelete(row: RowType) {
  try {
    await ElMessageBox.confirm('确定要删除该文物吗？', '提示', {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning',
    });

    const res = await ArtifactApi.deleteArtifact(row.id);
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


function onRefresh() {
  gridApi.query();
}


function onCreate() {
  formDrawerApi.setData({}).open();
}

const formOptions: VbenFormProps = {
  // 默认展开
  collapsed: false,
  schema: [
    {
      component: 'Input',
      fieldName: 'id',
      label: 'ID',
    },
    {
      component: 'Input',
      fieldName: 'originId',
      label: '原始ID',
    },
    {
      component: 'Input',
      fieldName: 'title',
      label: '名称',
    },
    {
      component: 'Input',
      fieldName: 'location',
      label: '位置',
    },
    {
      component: 'Input',
      fieldName: 'period',
      label: '年代',
    },
    {
      component: 'Input',
      fieldName: 'material',
      label: '材质',
    },
    {
      component: 'Input',
      fieldName: 'measurement',
      label: '尺寸',
    },
    {
      component: 'Input',
      fieldName: 'artist',
      label: '作者',
    },
    {
      component: 'Input',
      fieldName: 'creditLine',
      label: '来源',
    }
    ,
    {
      component: 'Input',
      fieldName: 'type',
      label: '类型',
    },
    {
      component: 'Input',
      fieldName: 'description',
      label: '描述',
    },
    {
      component: 'Input',
      fieldName: 'illusion',
      label: '注释',
    },
  ],
  // 控制表单是否显示折叠按钮
  showCollapseButton: true,
  // 是否在字段值改变时提交表单
  submitOnChange: true,
  // 按下回车时是否提交表单
  submitOnEnter: false,
};

const gridOptions: VxeTableGridOptions<RowType> = {
  checkboxConfig: {
    highlight: true,
    labelField: 'name',
  },

  columns: [
    { align: 'left', title: '',  type: 'checkbox',  width: 50},
    {
      title: '操作',
      width: 150,
      slots: {
        default: ({ row }) => h(
          'div',
          {
            style: { display: 'flex', gap: '8px' }
          },
          [
            h(ElButton, {
              onClick: () => onEdit(row),
              size: 'small',
              style: { marginRight: '8px' }
            }, '编辑'),
            h(ElButton, {
              onClick: () => onDelete(row),
              size: 'small',
              type: 'danger'
            }, '删除')
          ]
        )
      }
    },
    { field: 'id', title: 'ID' },
    { field: 'originId', title: '原始ID' },
    { field: 'title', title: '名称' },
    { field: 'url', title: '链接' },
    { field: 'image', title: '图片' },
    { field: 'location', title: '位置' },
    { field: 'period', title: '年代' },
    { field: 'material', title: '材质' },
    { field: 'measurement', title: '尺寸' },
    { field: 'artist', title: '作者' },
    { field: 'creditLine', title: '来源' },
    { field: 'type', title: '类型' },
    { field: 'description', title: '描述' },
    { field: 'illusion', title: '注释' },
  ],
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
        <el-button type="primary" @click="onCreate">新增</el-button>
      </template>
    </Grid>
  </Page>
</template>

<style scoped>

</style>

