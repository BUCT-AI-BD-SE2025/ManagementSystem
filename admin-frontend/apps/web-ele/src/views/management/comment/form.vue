<script setup lang="ts">
import type { Comment } from '#/types/Comment';
import {computed, ref} from 'vue';

import { useVbenForm } from '#/adapter/form';

import { useVbenDrawer } from '@vben/common-ui';
import { $t } from '#/locales';
import {CommentApi} from "#/api/management/comment";
import {useFormSchema} from "./data";
import {ElMessage} from "element-plus";

const emits = defineEmits(['success']);

const formData = ref<Comment>();
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
        await CommentApi.updateComment(id.value, values);
      } else {
        await CommentApi.createComment(values);
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
      const data = drawerApi.getData<Comment>();
      formApi.resetForm();
      if (data) {
        formData.value = data;
        id.value = data.commentId;
        formApi.setValues(data);
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
