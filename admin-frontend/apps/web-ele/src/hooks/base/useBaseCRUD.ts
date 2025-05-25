import { ref } from 'vue'
import { ElMessageBox, ElMessage } from 'element-plus'
export function useBaseCRUD<T, ID = string | number>({
  api,
  gridApi,
  formDrawerApi,
  idField = 'id' as keyof T,
}: {
  api: {
    deleteItem: (id: ID) => Promise<any>;
    batchDeleteItems: (ids: ID[]) => Promise<any>;
  }
  gridApi: any
  formDrawerApi?: any
  idField?: keyof T;
}) {

  function onRefresh() {
    gridApi.query()
  }


  const loading = ref(false)

  async function handleDelete(row: T & { id: ID}) {
      await ElMessageBox.confirm('确定要删除该项吗？', '提示', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning',
      })

      const res = await api.deleteItem(row.id)
      if (res) {
        ElMessage.success('删除成功')
        onRefresh?.()
      } else {
        ElMessage.error(res.message || '删除失败')
      }

  }

  async function handleBatchDelete() {
    const selectedRows = await gridApi.grid.getCheckboxRecords()

    if (!selectedRows.length) {
      ElMessage.warning('请至少选择一条记录')
      return
    }

    try {
      await ElMessageBox.confirm(`确定要删除选中的 ${selectedRows.length} 条记录？`, '提示', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning',
      })
      const ids = selectedRows.map((row: T) => row[idField as keyof T] as ID)
      const res = await api.batchDeleteItems(ids)

      if (res) {
        ElMessage.success('批量删除成功')
        onRefresh?.()
      } else {
        ElMessage.error(res.message || '批量删除失败')
      }
    } catch (error) {
      ElMessage.warning('操作已取消')
    }
  }
  function handleCreate() {
    formDrawerApi.setData({}).open()
  }

  function handleEdit(row: T) {
    formDrawerApi.setData(row).open()
  }
  return {
    handleDelete,
    handleBatchDelete,
    handleCreate,
    handleEdit,
    loading,
  }

}
