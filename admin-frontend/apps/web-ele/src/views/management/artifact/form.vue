<script setup lang="ts">

import {computed, ref} from 'vue';

import { useVbenForm } from '#/adapter/form';

import { useVbenDrawer } from '@vben/common-ui';
import { $t } from '#/locales';
import {ArtifactApi} from "#/api/management/artifact";

import type {Artifact} from "#/types/Artifact";
import {useFormSchema} from "./data";

const emits = defineEmits(['success']);

const formData = ref<Artifact>();
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

    if (id.value) {
      // 更新用户
      await ArtifactApi.updateArtifact(id.value, values);
    } else {
      // 创建用户
      await ArtifactApi.createArtifact(values);
    }

    emits('success');
    await drawerApi.close();
  },
  onOpenChange(isOpen) {
    if (isOpen) {
      const data = drawerApi.getData<Artifact>();
      formApi.resetForm();
      if (data) {
        formData.value = data;
        id.value = data.id;
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

