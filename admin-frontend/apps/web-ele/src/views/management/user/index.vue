<script setup lang="ts">

import type { VbenFormProps} from '#/adapter/form';
import {useVbenVxeGrid, type VxeTableGridOptions} from "#/adapter/vxe-table";

import {Page, useVbenDrawer} from '@vben/common-ui';

import { ElButton, ElMessage, ElMessageBox } from 'element-plus';

import { h } from 'vue'

import {UserApi} from "#/api/management/user";
import getUserList = UserApi.getUserList;

import UserForm from "#/views/management/user/userForm.vue";

const [FormDrawer, formDrawerApi] = useVbenDrawer({
  connectedComponent: UserForm,
  destroyOnClose: true,
})

interface RowType {
  uid: string;
  username: string;
  nickname: string;
  password: string;
  email: string;
  phone: string;
  sex: string;
  ip: string;
  avatarUrl: string;
  status: string;
  lastLogin: string | null;
  createdAt: string;
  updatedAt: string;
}

function onEdit(row: RowType) {
  formDrawerApi.setData(row).open()
}

async function onDelete(row: RowType) {
  try {
    await ElMessageBox.confirm('确定要删除该用户吗？', '提示', {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning',
    });

    const res = await UserApi.deleteUser(row.uid);
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
      fieldName: 'uid',
      label: 'ID',
    },
    {
      component: 'Input',
      fieldName: 'username',
      label: '用户名',
    },
    {
      component: 'Input',
      fieldName: 'email',
      label: '邮箱',
    },
    {
      component: 'Input',
      fieldName: 'phone',
      label: '手机号',
    },
    {
      component: 'Select',
      fieldName: 'sex',
      label: '性别',
      componentProps: {
        options: [
          { label: '全部', value: '' },
          { label: '男', value: '0' },
          { label: '女', value: '1' },
          { label: '未知', value: '2' },
        ],
      },
    },
    {
      component: 'Select',
      fieldName: 'status',
      label: '状态',
      componentProps: {
        options: [
          { label: '全部', value: '' },
          { label: '激活', value: 'ACTIVE' },
          { label: '锁定', value: 'LOCKED' },
          { label: '删除', value: 'DELETED' },
        ],
      },
    },
    {
      component: 'DatePicker',
      fieldName: 'createdAt',
      label: '创建时间',
      componentProps: {
        type: 'daterange',
        rangeSeparator: '至',
        startPlaceholder: '开始日期',
        endPlaceholder: '结束日期',
      },
    },
    {
      component: 'DatePicker',
      fieldName: 'loginAt',
      label: '登录时间',
      componentProps: {
        type: 'daterange',
        rangeSeparator: '至',
        startPlaceholder: '开始日期',
        endPlaceholder: '结束日期',
      },
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
    { field: 'uid', title: 'ID', width: 50 },
    { field: 'username', title: '用户名', width: 100 },
    { field: 'nickname', title: '昵称', width: 100 },
    { field: 'email', title: '电子邮箱', width: 150 },
    { field: 'phone', title: '手机号',width: 150 },
    { field: 'sex', title: '性别',width: 50,
      formatter: ({ cellValue }) =>
        cellValue === '0' ? '男' :
      cellValue === '1' ? '女' : '未知',
    },
    { field: 'ip', title: 'IP',width: 150 },
    { field: 'avatarUrl', title: '头像Url', width: 150 },
    { field: 'status', title: '状态', width: 100 },
    { field: 'lastLogin', title: '最后登录', width: 150 },
    { field: 'createdAt', title: '创建时间', width: 150 },
    { field: 'updatedAt', title: '更新时间', width: 150 },

  ],
  exportConfig: {},
  height: 'auto',
  keepSource: true,
  pagerConfig: {},
  proxyConfig: {
    ajax: {
      query: async ({page}, formValues) => {
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
        console.log(params);

        const res = await getUserList(params);
        // ElMessage.success(`加载成功`);
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
