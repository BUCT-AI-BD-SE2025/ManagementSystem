<script setup lang="ts">

import type { VbenFormProps} from '#/adapter/form';
import {useVbenVxeGrid, type VxeTableGridOptions} from "#/adapter/vxe-table";

import { Page } from '@vben/common-ui';

import { ElMessage as message } from 'element-plus'
import {ArtifactApi} from "#/api/management/artifact";
import getArtifactList = ArtifactApi.getArtifactList;

interface RowType {
  id: number
  originId: string
  title: string
  url: string
  image: string
  location: string
  period: string
  material: string
  measurement: string
  artist: string
  creditLine: string
  type: string
  description: string
  illusion: string
}

const formOptions: VbenFormProps = {
  // 默认展开
  collapsed: false,
  schema: [
    {
      component: 'Input',
      defaultValue: '1',
      fieldName: 'title',
      label: 'Title',
    },
    {
      component: 'Input',
      fieldName: 'productName',
      label: 'ProductName',
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
    { field: 'id', title: 'ID' },
    { field: 'originId', title: '原始ID' },
    { field: 'title', title: '名称' },
    { field: 'url', title: '连接' },
    { field: 'image', title: '图片' },
    { field: 'location', title: '所在地' },
    { field: 'period', title: '所属朝代' },
    { field: 'material', title: '材质' },
    { field: 'measurement', title: '尺寸' },
    { field: 'artist', title: '作者' },
    { field: 'type', title: '类型' },
    { field: 'description', title: '描述' },
    { field: 'illusion', title: '异体' },
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
        message.success(`加载成功`);
        const list:  any = {};
        list.items = res.records;
        list.total = res.total;
        console.log(list)
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

const [Grid] = useVbenVxeGrid({
  formOptions,
  gridOptions,
});

</script>

<template>
  <Page auto-content-height>
    <Grid />
  </Page>
</template>

<style scoped>

</style>
