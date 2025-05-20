<script setup lang="ts">
import type { User } from '#/types/User';

import {computed, ref} from 'vue';

import { useVbenForm } from '#/adapter/form';

import { useVbenDrawer } from '@vben/common-ui';
import { $t } from '#/locales';
import {ArtifactApi} from "#/api/management/artifact";

const emits = defineEmits(['success']);

const formData = ref<User>();
const id = ref<string>();

const [Form, formApi] = useVbenForm({
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
      fieldName: 'url',
      label: '链接',
    },
    {
      component: 'Input',
      fieldName: 'image',
      label: '图片',
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
    },
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

