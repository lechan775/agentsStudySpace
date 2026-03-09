<template>
  <div class="category-management">
    <div class="toolbar">
      <el-button type="primary" @click="showAddDialog">添加分类</el-button>
    </div>

    <el-table :data="categories" stripe style="margin-top: 20px">
      <el-table-column prop="id" label="ID" width="60"></el-table-column>
      <el-table-column prop="name" label="分类名称"></el-table-column>
      <el-table-column prop="description" label="描述"></el-table-column>
      <el-table-column prop="productCount" label="商品数量" width="100"></el-table-column>
      <el-table-column label="操作" width="150">
        <template #default="{ row }">
          <el-button size="small" @click="editCategory(row)">编辑</el-button>
          <el-button size="small" type="danger" @click="deleteCategory(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="dialogVisible" :title="isEdit ? '编辑分类' : '添加分类'" width="500px">
      <el-form :model="categoryForm" label-width="80px">
        <el-form-item label="分类名称">
          <el-input v-model="categoryForm.name"></el-input>
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="categoryForm.description" type="textarea"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveCategory">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'

const categories = ref([])
const dialogVisible = ref(false)
const isEdit = ref(false)
const categoryForm = reactive({ id: null, name: '', description: '' })

const loadCategories = () => {
  categories.value = [
    { id: 1, name: '水果', description: '新鲜水果', productCount: 25 },
    { id: 2, name: '蔬菜', description: '新鲜蔬菜', productCount: 18 },
    { id: 3, name: '肉类', description: '新鲜肉类', productCount: 12 },
    { id: 4, name: '海鲜', description: '新鲜海鲜', productCount: 15 }
  ]
}

const showAddDialog = () => {
  isEdit.value = false
  categoryForm.id = null
  categoryForm.name = ''
  categoryForm.description = ''
  dialogVisible.value = true
}

const editCategory = (row) => {
  isEdit.value = true
  Object.assign(categoryForm, row)
  dialogVisible.value = true
}

const saveCategory = () => {
  ElMessage.success(isEdit.value ? '分类更新成功' : '分类添加成功')
  dialogVisible.value = false
  loadCategories()
}

const deleteCategory = async (row) => {
  try {
    await ElMessageBox.confirm('确定要删除该分类吗？', '提示', { type: 'warning' })
    categories.value = categories.value.filter(c => c.id !== row.id)
    ElMessage.success('删除成功')
  } catch {}
}

onMounted(() => {
  loadCategories()
})
</script>

<style scoped>
.category-management {
  padding: 20px;
}
</style>
