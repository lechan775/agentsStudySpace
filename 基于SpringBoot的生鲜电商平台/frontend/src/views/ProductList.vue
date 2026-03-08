<template>
  <div class="product-list">
    <div class="filter-section">
      <el-select v-model="selectedCategory" placeholder="选择分类" clearable @change="loadProducts">
        <el-option label="全部" :value="null" />
        <el-option v-for="cat in categories" :key="cat.id" :label="cat.name" :value="cat.id" />
      </el-select>
      <el-input v-model="searchKeyword" placeholder="搜索商品" clearable style="width: 300px" @change="loadProducts">
        <template #append>
          <el-button :icon="Search" @click="loadProducts" />
        </template>
      </el-input>
    </div>
    
    <el-row :gutter="20">
      <el-col v-for="product in products" :key="product.id" :xs="12" :sm="8" :md="6" :lg="4">
        <el-card class="product-card" shadow="hover" @click="goToDetail(product.id)">
          <img :src="product.imageUrl || 'https://via.placeholder.com/200'" class="product-image" />
          <div class="product-info">
            <h3>{{ product.name }}</h3>
            <p class="price">¥{{ product.price }}</p>
            <p class="stock">库存: {{ product.stock }}{{ product.unit }}</p>
            <el-button type="primary" size="small" @click.stop="addToCart(product)">
              加入购物车
            </el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>
    
    <el-pagination
      v-model:current-page="currentPage"
      :page-size="pageSize"
      :total="total"
      layout="total, prev, pager, next"
      @current-change="loadProducts"
    />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { Search } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import { getProducts, getCategories } from '../api'
import { useCartStore } from '../stores/cart'

const router = useRouter()
const cartStore = useCartStore()

const products = ref([])
const categories = ref([])
const selectedCategory = ref(null)
const searchKeyword = ref('')
const currentPage = ref(1)
const pageSize = ref(12)
const total = ref(0)

const loadProducts = async () => {
  const params = {
    page: currentPage.value - 1,
    size: pageSize.value
  }
  if (selectedCategory.value) params.categoryId = selectedCategory.value
  if (searchKeyword.value) params.keyword = searchKeyword.value
  
  const res = await getProducts(params)
  if (res.code === 200) {
    products.value = res.data.content || res.data
    total.value = res.data.totalElements || products.value.length
  }
}

const loadCategories = async () => {
  const res = await getCategories()
  if (res.code === 200) {
    categories.value = res.data
  }
}

const addToCart = async (product) => {
  await cartStore.addItem(product.id, 1)
  ElMessage.success('已添加到购物车')
}

const goToDetail = (id) => {
  router.push(`/product/${id}`)
}

onMounted(() => {
  loadProducts()
  loadCategories()
})
</script>

<style scoped>
.product-list { padding: 20px; }
.filter-section { display: flex; gap: 15px; margin-bottom: 20px; }
.product-card { margin-bottom: 20px; cursor: pointer; transition: transform 0.2s; }
.product-card:hover { transform: translateY(-5px); }
.product-image { width: 100%; height: 180px; object-fit: cover; }
.product-info h3 { font-size: 16px; margin: 10px 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; }
.price { color: #f56c6c; font-size: 20px; font-weight: bold; }
.stock { color: #909399; font-size: 14px; margin-bottom: 10px; }
</style>
