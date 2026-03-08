<template>
  <div class="home-page">
    <div class="sidebar">
      <h3>商品分类</h3>
      <el-menu
        :default-active="activeCategory"
        @select="handleCategorySelect"
      >
        <el-menu-item index="">
          <el-icon><Grid /></el-icon>
          <span>全部商品</span>
        </el-menu-item>
        <el-menu-item 
          v-for="category in categories" 
          :key="category.id" 
          :index="String(category.id)"
        >
          <el-icon><Folder /></el-icon>
          <span>{{ category.name }}</span>
        </el-menu-item>
      </el-menu>
    </div>
    
    <div class="main-content">
      <div class="product-header">
        <h2>{{ categoryName }}</h2>
        <div class="sort-options">
          <el-radio-group v-model="sortBy" size="small">
            <el-radio-button value="default">默认</el-radio-button>
            <el-radio-button value="price_asc">价格↑</el-radio-button>
            <el-radio-button value="price_desc">价格↓</el-radio-button>
          </el-radio-group>
        </div>
      </div>
      
      <el-empty v-if="!loading && !products.length" description="暂无商品" />
      
      <div v-else class="product-grid">
        <el-card 
          v-for="product in products" 
          :key="product.id" 
          class="product-card"
          @click="$router.push(`/product/${product.id}`)"
        >
          <div class="product-image">
            <img :src="product.image || '/placeholder.png'" :alt="product.name" />
          </div>
          <div class="product-info">
            <h4>{{ product.name }}</h4>
            <p class="description">{{ product.description }}</p>
            <div class="product-bottom">
              <span class="price">¥{{ product.price }}</span>
              <el-button 
                type="primary" 
                size="small"
                @click.stop="handleAddToCart(product)"
              >
                加入购物车
              </el-button>
            </div>
          </div>
        </el-card>
      </div>
      
      <div v-if="total > pageSize" class="pagination">
        <el-pagination
          v-model:current-page="currentPage"
          :page-size="pageSize"
          :total="total"
          layout="prev, pager, next"
          @current-change="fetchProducts"
        />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed, watch } from 'vue'
import { useRoute } from 'vue-router'
import { Grid, Folder } from '@element-plus/icons-vue'
import { productAPI, categoryAPI } from '../api'
import { useCartStore } from '../stores/cart'
import { ElMessage } from 'element-plus'

const route = useRoute()
const cartStore = useCartStore()

const products = ref([])
const categories = ref([])
const loading = ref(false)
const activeCategory = ref('')
const sortBy = ref('default')
const currentPage = ref(1)
const pageSize = ref(12)
const total = ref(0)

const categoryName = computed(() => {
  if (!activeCategory.value) return '全部商品'
  const cat = categories.value.find(c => c.id === Number(activeCategory.value))
  return cat?.name || '商品列表'
})

const fetchCategories = async () => {
  try {
    const res = await categoryAPI.getList()
    categories.value = res.data || res || []
  } catch (error) {
    console.error('获取分类失败:', error)
  }
}

const fetchProducts = async () => {
  loading.value = true
  try {
    const params = {
      page: currentPage.value,
      size: pageSize.value,
      categoryId: activeCategory.value || undefined,
      sort: sortBy.value,
      search: route.query.search || undefined
    }
    const res = await productAPI.getList(params)
    products.value = res.data || res || []
    total.value = res.total || products.value.length
  } catch (error) {
    console.error('获取商品失败:', error)
    ElMessage.error('获取商品失败')
  } finally {
    loading.value = false
  }
}

const handleCategorySelect = (index) => {
  activeCategory.value = index
  currentPage.value = 1
  fetchProducts()
}

const handleAddToCart = async (product) => {
  if (!localStorage.getItem('token')) {
    ElMessage.warning('请先登录')
    return
  }
  const result = await cartStore.addItem(product.id, 1)
  if (result.success) {
    ElMessage.success('已加入购物车')
  } else {
    ElMessage.error(result.message)
  }
}

watch(() => route.query.search, () => {
  fetchProducts()
})

watch(sortBy, () => {
  fetchProducts()
})

onMounted(() => {
  fetchCategories()
  if (route.query.search) {
    activeCategory.value = ''
  }
  fetchProducts()
})
</script>

<style scoped>
.home-page {
  display: flex;
  gap: 20px;
}

.sidebar {
  width: 200px;
  flex-shrink: 0;
}

.sidebar h3 {
  margin-bottom: 15px;
  font-size: 16px;
}

.main-content {
  flex: 1;
}

.product-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.product-header h2 {
  margin: 0;
}

.product-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
  gap: 20px;
}

.product-card {
  cursor: pointer;
  transition: transform 0.2s, box-shadow 0.2s;
}

.product-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.product-image {
  height: 160px;
  overflow: hidden;
  background: #f5f5f5;
}

.product-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.product-info h4 {
  margin: 10px 0;
  font-size: 16px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.description {
  color: #999;
  font-size: 12px;
  margin: 0 0 10px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.product-bottom {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.price {
  color: #f56c6c;
  font-size: 18px;
  font-weight: bold;
}

.pagination {
  margin-top: 30px;
  display: flex;
  justify-content: center;
}
</style>
