<script setup lang="ts">

import {computed, ref} from 'vue';

import { useVbenForm } from '#/adapter/form';
import {VbenTree} from "@vben/common-ui";
import { useVbenDrawer } from '@vben/common-ui';
import { $t } from '#/locales';
import {RoleApi} from "#/api/management/role";
import type {Role as DataType} from "#/types/Role";
import {useFormSchema} from "./data";
import { IconifyIcon } from '@vben/icons';
import {ElMessage,vLoading} from "element-plus";
import {PermissionApi} from "#/api/management/permission";
const emits = defineEmits(['success']);

const formData = ref<DataType>();
const id = ref<string>();

const [Form, formApi] = useVbenForm({
  schema: useFormSchema(),
  showDefaultActions: false,
});
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
  onOpenChange(isOpen) {
    if (isOpen) {
      const data = drawerApi.getData<DataType>();
      formApi.resetForm();
      if (data) {
        formData.value = data;
        id.value = data.roleId;
        formApi.setValues(data);
      } else {
        id.value = undefined;
      }
      if (permissions.value.length === 0) {
        loadPermissions();
        formApi.setState((prev) =>{
          const currentSchema = prev?.schema ?? [];

          const permissionField = {
            component: 'Tree',
            componentProps: {
              treeData: permissions.value,
              multiple: true,
              valueField: 'permId',
              labelField: 'meta.title',
              iconField: 'meta.icon',
              defaultExpandedLevel: 2,
            },
            fieldSlots: {
              default: 'permissions',
            },
            fieldName: 'permissions',
            label: '权限配置',
          };

          return {
            schema: [...currentSchema, permissionField],
          };
        });
      }
    }
  },
});

const permissions = ref<any[]>([]);
const loadingPermissions = ref(false);

async function loadPermissions() {
  loadingPermissions.value = true;
  try {
    const res = await PermissionApi.getPermissionList({isAll: true});
    permissions.value = res.records;
    console.log(permissions.value);
  } finally {
    loadingPermissions.value = false;
  }
}

const getDrawerTitle = computed(() => {
  return id.value ? ($t('common.edit')) : ($t('common.create'));
});
</script>

<template>
  <Drawer :title="getDrawerTitle">
    <Form>
      <template #permissions="slotProps">
        <div v-loading="loadingPermissions" class="w-full">
          <VbenTree
            v-model:value="slotProps.fieldValue"
            :tree-data="permissions"
            multiple
            bordered
            :default-expanded-level="2"
            value-field="id"
            label-field="meta.title"
            icon-field="meta.icon"
          >
            <template #node="{ value }">
              <IconifyIcon v-if="value.meta.icon" :icon="value.meta.icon" />
              {{ $t(value.meta.title) }}
            </template>
          </VbenTree>
        </div>
      </template>
    </Form>
  </Drawer>
</template>

