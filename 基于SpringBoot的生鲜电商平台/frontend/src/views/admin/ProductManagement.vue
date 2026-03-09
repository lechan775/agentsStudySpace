<template>
  <div class="product-management">
    <div class="toolbar">
      <el-button type="primary" @click="showAddDialog">添加商品</el-button>
      <el-input v-model="searchKeyword" placeholder="搜索商品" style="width: 300px; margin-left: 10px">
        <template #append>
          <el-button @click="loadProducts">搜索</el-button>
        </template>
      </el-input>
    </div>

    <el-table :data="products" stripe v-loading="loading" style="margin-top: 20px">
      <el-table-column prop="id" label="ID" width="60"></el-table-column>
      <el-table-column prop="name" label="商品名称" width="180"></el-table-column>
      <el-table-column prop="category.name" label="分类" width="100"></el-table-column>
      <el-table-column prop="price" label="价格" width="100">
        <template #default="{ row }">¥{{ row.price }}</template>
      </el-table-column>
      <el-table-column prop="stock" label="库存" width="80"></el-table-column>
      <el-table-column prop="salesCount" label="销量" width="80"></el-table-column>
      <el-table-column prop="isActive" label="状态" width="80">
        <template #default="{ row }">
          <el-tag :type="row.isActive ? 'success' : 'danger'">
            {{ row.isActive ? '上架' : '下架' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" fixed="right" width="200">
        <template #default="{ row }">
          <el-button size="small" @click="editProduct(row)">编辑</el-button>
          <el-button size="small" :type="row.isActive ? 'warning' : 'success'" @click="toggleStatus(row)">
            {{ row.isActive ? '下架' : '上架' }}
          </el-button>
          <el-button size="small" type="danger" @click="deleteProduct(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      v-model:current-page="currentPage"
      :page-size="pageSize"
      :total="total"
      layout="total, prev, pager, next"
      style="margin-top: 20px; justify-content: flex-end"
      @current-change="loadProducts"
    />

    <!-- Add/Edit Dialog -->
    <el-dialog v-model="dialogVisible" :title="isEdit ? '编辑商品' : '添加商品'" width="600px">
      <el-form :model="productForm" label-width="80px">
        <el-form-item label="商品名称">
          <el-input v-model="productForm.name"></el-input>
        </el-form-item>
        <el-form-item label="分类">
          <el-select v-model="productForm.categoryId" placeholder="选择分类">
            <el-option v-for="cat in categories" :key="cat.id" :label="cat.name" :value="cat.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="价格">
          <el-input-number v-model="productForm.price" :min="0" :precision="2"></el-input-number>
        </el-form-item>
        <el-form-item label="库存">
          <el-input-number v-model="productForm.stock" :min="0"></el-input-number>
        </el-form-item>
        <el-form-item label="单位">
          <el-input v-model="productForm.unit"></el-input>
        </el-form-item>
        <el-form-item label="商品描述">
          <el-input v-model="productForm.description" type="textarea" :rows="3"></el-input>
        </el-form-item>
        <el-form-item label="图片URL">
          <el-input v-model="productForm.imageUrl"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveProduct">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'

const products = ref([])
const categories = ref([])
const loading = ref(false)
const searchKeyword = ref('')
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

const dialogVisible = ref(false)
const isEdit = ref(false)
const productForm = reactive({
  id: null,
  name: '',
  categoryId: null,
  price: 0,
  stock: 0,
  unit: '斤',
  description: '',
  imageUrl: ''
})

const loadProducts = async () => {
  loading.value = true
  // Mock data
  setTimeout(() => {
    products.value = [
      { id: 1, name: '新鲜草莓', category: { name: '水果' }, price: 29.9, stock: 100, salesCount: 156, isActive: true },
      { id: 2, name: '有机西兰花', category: { name: '蔬菜' }, price: 12.8, stock: 50, salesCount: 87, isActive: true },
      { id: 3, name: '进口车厘子', category: { name: '水果' }, price: 89.0, stock: 30, salesCount: 98, isActive: true }
    ]
    total.value = 3
    loading.value = false
  }, 300)
}

const loadCategories = async () => {
  categories.value = [
    { id: 1, name: '水果' },
    { id: 2, name: '蔬菜' },
    { id: 3, name: '肉类' },
    { id: 4, name: '海鲜' }
  ]
}

const showAddDialog = () => {
  isEdit.value = false
  Object.assign(productForm, {
    id: null,
    name: '',
    categoryId: null,
    price: 0,
    stock: 0,
    unit: '斤',
    description: '',
    imageUrl: ''
  })
  dialogVisible.value = true
}

const editProduct = (row) => {
  isEdit.value = true
  Object.assign(productForm, row)
  dialogVisible.value = true
}

const saveProduct = () => {
  ElMessage.success(isEdit.value ? '商品更新成功' : '商品添加成功')
  dialogVisible.value = false
  loadProducts()
}

const toggleStatus = async (row) => {
  row.isActive = !row.isActive
  ElMessage.success(row.isActive ? '商品已上架' : '商品已下架')
}

const deleteProduct = async (row) => {
  try {
    await ElMessageBox.confirm('确定要删除该商品吗？', '提示', { type: 'warning' })
    products.value = products.value.filter(p => p.id !== row.id)
    ElMessage.success('删除成功')
  } catch {}
}

onMounted(() => {
  loadProducts()
  loadCategories()
})
</script>

<style scoped>
.product-management {
  padding: 20px;
}
.toolbar {
  display: flex;
  align-items: center;
}
</style>
