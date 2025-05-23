<script setup lang="ts">

import {computed, ref} from 'vue';

import {useVbenForm} from '#/adapter/form';
import { useVbenDrawer } from '@vben/common-ui';
import { $t } from '#/locales';
import {RoleApi} from "#/api/management/role";
import type {Role as DataType} from "#/types/Role";
import {ElMessage} from "element-plus";
import {PermissionApi} from "#/api/management/permission";
const emits = defineEmits(['success']);


const formData = ref<DataType>();
const id = ref<string>();
const permissionsOptions = ref([])as any;

const [Form, formApi] = useVbenForm({
  schema: [
    {
      component: 'Input',
      fieldName: 'roleId',
      label: 'ID',
    },
    {
      component: 'Input',
      fieldName: 'roleName',
      label: '名称',
    },
    {
      component: 'Input',
      fieldName: 'roleCode',
      label: '角色码',
    },
    {
      component: 'Input',
      fieldName: 'description',
      label: '描述',
    },
    {
      component: 'CheckboxGroup',
      fieldName: 'permissions',
      label: '权限',
      componentProps: {
        options: permissionsOptions,
      },
    }
    ],
  showDefaultActions: false,
});
async function loadPermissions() {
  try {
    const res = await PermissionApi.getAllPermission(); // 假设接口返回所有权限列表
    permissionsOptions.value = res.map(item => ({
      label: item.permName,
      value: item.permCode,
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
        await RoleApi.updateRole(id.value, values);
      } else {
        await RoleApi.createRole(values);
      }
      if (values.permissions && values.permissions.length > 0) {
        await RoleApi.assignPermissions(id.value!, values.permissions);
      }

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
      await loadPermissions();

      const data = drawerApi.getData<DataType>();
      await formApi.resetForm();
      if (data) {
        formData.value = data;
        id.value = data.roleId;
        const rolePermRes = await RoleApi.getRolePermissions(data.roleId);
        const rolePerms = rolePermRes.map(p => p.permCode); // ['perm1', 'perm2']
        await formApi.setValues({ ...data, permissions: rolePerms });
      } else {
        id.value = undefined;
      }
    }
  },
});


const getDrawerTitle = computed(() => {
  return id.value ? ($t('common.edit')) : ($t('common.create'));
});
</script>

<template>
  <Drawer :title="getDrawerTitle">
    <Form>
    </Form>
  </Drawer>
</template>

