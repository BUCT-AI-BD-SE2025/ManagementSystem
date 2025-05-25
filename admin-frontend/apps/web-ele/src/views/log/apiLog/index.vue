<script setup lang="ts">

import type { VbenFormProps } from '#/adapter/form';
import {useVbenVxeGrid, type VxeTableGridOptions} from "#/adapter/vxe-table";

import { Page, } from '@vben/common-ui';

import {LoginLogApi} from "#/api/log/loginLog";


import { useColumns } from "./data";
import {useGridSchema} from "./data";
import {useBaseGridOptions} from "#/hooks/base/useBaseGridOptions";


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
const gridOptions: VxeTableGridOptions = useBaseGridOptions(useColumns(), LoginLogApi.getLoginLogList,
  {
    proxyConfig: {
      ajax: {
        query: async ({ page }, formValues) => {
          const params = {
            page: page.currentPage,
            pageSize: page.pageSize,
            ...formValues,
            startTime: formValues.loginTime?.[0],
            endTime: formValues.loginTime?.[1],
          }
          delete params.loginTime;

          const res = await LoginLogApi.getLoginLogList(params);
          return {
            items: res.records,
            total: res.total,
          };
        },
      },
    }
  }
);
const [Grid] = useVbenVxeGrid({
  formOptions,
  gridOptions,
});




</script>

<template>
  <Page auto-content-height>
    <Grid>

    </Grid>
  </Page>
</template>

<style scoped>

</style>

