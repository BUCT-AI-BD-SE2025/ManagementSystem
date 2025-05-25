<script setup lang="ts">
import type { User } from '#/types/User';
import {computed, ref} from 'vue';

import { useVbenForm } from '#/adapter/form';

import { useVbenDrawer } from '@vben/common-ui';
import { $t } from '#/locales';
import {UserApi} from "#/api/management/user";
import {ElMessage} from "element-plus";
import {RoleApi} from "#/api/management/role";

const emits = defineEmits(['success']);

const formData = ref<User>();
const id = ref<string>();
const roleOptions = ref([])as any;

const [Form, formApi] = useVbenForm({
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
      fieldName: 'nickname',
      label: '昵称',
    },
    {
      component: 'Input',
      fieldName: 'avatarUrl',
      label: '头像Url',
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
    },
    {
      component: 'CheckboxGroup',
      fieldName: 'roles',
      label: '角色',
      componentProps: {
        options: roleOptions,
      }
    }
  ],
  showDefaultActions: false,
});

async function loadRoles(){
  try {
    const res = await RoleApi.getAllRoles();
    roleOptions.value = res.map(item => ({
      label: item.roleName,
      value: item.roleCode,
    }));
  } catch (e) {
    ElMessage.error("加载权限失败");
  }
}


const [Drawer, drawerApi] = useVbenDrawer({
  async onConfirm() {
    const { valid } = await formApi.validate();
    if (!valid) return;

    const values = await formApi.getValues();

    drawerApi.lock();
    try {
      if (id.value) {
        await UserApi.updateUser(id.value, values);
      } else {
        await UserApi.createUser(values);
      }
      if (values.roles && values.roles.length > 0)
        await UserApi.assignRoles(id.value!, values.roles);

    } catch (e){
      ElMessage.error("提交失败，请重试");
    } finally {
      drawerApi.unlock();
    }

    emits('success');
    await drawerApi.close();
  },
  async onOpenChange(isOpen) {
    if (isOpen) {
      await loadRoles();

      const data = drawerApi.getData<User>();
      formApi.resetForm();
      if (data) {
        formData.value = data;
        id.value = data.uid;
        const userRoleRes = await UserApi.getUserRoles(data.uid);
        const userRoles = userRoleRes.map(p => p.roleCode); // ['perm1', 'perm2']

        await formApi.setValues({ ...data, roles: userRoles });
      } else {
        id.value = undefined;
      }
    }
  },
});

// 抽屉标题动态显示“新增”或“编辑”
const getDrawerTitle = computed(() => {
  return id.value ? $t('common.edit') : $t('common.create');
});
</script>

<template>
  <Drawer :title="getDrawerTitle">
    <Form />
  </Drawer>
</template>
