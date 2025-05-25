<script lang="ts" setup>
import { ref } from 'vue';

import { useVbenModal } from '@vben/common-ui';
import {useVbenForm} from "#/adapter/form";
import {CommentApi} from "#/api/management/comment";

const data = ref();
const [Form, formApi] = useVbenForm({
  schema: [{
    component: 'Input',
    fieldName: 'reason',
    label: '原因',
    componentProps: {
      placeholder: '请输入原因',
    },
    defaultValue: '',
  }],
  showDefaultActions: false,
});

async function submit(data: any)
{
  await CommentApi.batchUpdateStatus({
    ids: data.ids,
    reason: data.reason,
    status: data.status,
  })
}
const [Modal, modalApi] = useVbenModal({
  onCancel() {
    modalApi.close();
  },
  async onConfirm() {
    const value = await formApi.getValues();
    data.value = {
      ...data.value,
      ...value,
    }

    await submit(data.value)
    data.value.gridApi.query();
    await modalApi.close();
  },
  onOpenChange(isOpen: boolean) {
    if (isOpen) {
      data.value = modalApi.getData<Record<string, any>>();
    }
  },
});
</script>
<template>
  <Modal title="请输入通过理由">
    <Form />
  </Modal>
</template>
