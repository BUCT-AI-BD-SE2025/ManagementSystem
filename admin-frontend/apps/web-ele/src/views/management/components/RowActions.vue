<script setup lang="ts">
import {ElButton} from "element-plus";

defineProps<{
  row: any;
  onEdit: (row: any) => void;
  onDelete: (row: any) => void;
}>();
</script>

<template>
  <div style="display: flex; gap: 8px;">
    <ElButton size="small" type="primary" @click="onEdit(row)">
      编辑
    </ElButton>
    <ElButton size="small" type="danger" @click="onDelete(row)">
      删除
    </ElButton>
  </div>
</template>
