<script setup lang="ts">
import type { User } from '#/types/User';

import {computed, ref} from 'vue';

import { useVbenForm } from '#/adapter/form';

import { useVbenDrawer } from '@vben/common-ui';
import { $t } from '#/locales';
import {UserApi} from "#/api/management/user";

const emits = defineEmits(['success']);

const formData = ref<User>();
const id = ref<string>();

const [Form, formApi] = useVbenForm({
  schema: [
    {
      component: 'Input',
      fieldName: 'username',
      label: '用户名',
    },
    {
      component: 'Input',
      fieldName: 'nickname',
      label: '昵称',
    },
    {
      component: 'Input',
      fieldName: 'password',
      label: '密码',
      componentProps: {
        type: 'password',
        showPassword: true,
      },
    },
    {
      component: 'Input',
      fieldName: 'email',
      label: '电子邮箱',
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
          { label: '激活', value: 'ACTIVE' },
          { label: '锁定', value: 'LOCKED' },
          { label: '删除', value: 'DELETED'},
        ],
      },
    }

  ],
  showDefaultActions: false,
});

const [Drawer, drawerApi] = useVbenDrawer({
  async onConfirm() {
    const { valid } = await formApi.validate();
    if (!valid) return;

    const values = await formApi.getValues();

    drawerApi.lock();

    if (id.value) {
      // 更新用户
      await UserApi.updateUser(id.value, values);
    } else {
      // 创建用户
      await UserApi.createUser(values);
    }

    emits('success');
    drawerApi.close();
  },
  onOpenChange(isOpen) {
    if (isOpen) {
      const data = drawerApi.getData<User>();
      formApi.resetForm();
      if (data) {
        formData.value = data;
        id.value = data.uid;
        formApi.setValues(data);
      } else {
        id.value = undefined;
      }
    }
  },
});

// 抽屉标题动态显示“新增”或“编辑”
const getDrawerTitle = computed(() => {
  return id.value ? $t('common.edit', $t('system.user.name')) : $t('common.create', $t('system.user.name'));
});
</script>

<template>
  <Drawer :title="getDrawerTitle">
    <Form />
  </Drawer>
</template>
