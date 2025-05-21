<script setup lang="ts">

import {computed, ref} from 'vue';

import { useVbenForm } from '#/adapter/form';

import { useVbenDrawer } from '@vben/common-ui';
import { $t } from '#/locales';
import {PermissionApi} from "#/api/management/permission";
import type {Permission as DataType} from "#/types/Permission";
import {useFormSchema} from "./data";

import {ElMessage} from "element-plus";
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
        await PermissionApi.updatePermission(id.value, values);
      } else {
        await PermissionApi.createPermission(values);
      }
    } catch (e){
      ElMessage.error("提交失败，请重试");
    } finally {
      drawerApi.unlock();
    }

    emits('success');
    drawerApi.close();
  },
  onOpenChange(isOpen) {
    if (isOpen) {
      const data = drawerApi.getData<DataType>();
      formApi.resetForm();
      if (data) {
        formData.value = data;
        id.value = data.permId;
        formApi.setValues(data);
      } else {
        id.value = undefined;
      }
    }
  },
});

// 抽屉标题动态显示“新增”或“编辑”
const getDrawerTitle = computed(() => {
  return id.value ? ($t('common.edit')) : ($t('common.create'));
});
</script>

<template>
  <Drawer :title="getDrawerTitle">
    <Form />
  </Drawer>
</template>

