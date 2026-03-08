<template>
  <div class="product-detail" v-loading="loading">
    <el-card v-if="product">
      <el-row :gutter="40">
        <el-col :span="10">
          <img :src="product.imageUrl || 'https://via.placeholder.com/400'" class="main-image" />
        </el-col>
        <el-col :span="14">
          <h1>{{ product.name }}</h1>
          <p class="description">{{ product.description }}</p>
          <p class="price">¥{{ product.price }} / {{ product.unit }}</p>
          <p class="stock">库存: {{ product.stock }}{{ product.unit }}</p>
          <p v-if="product.expiryDate" class="expiry">保质期至: {{ product.expiryDate }}</p>
          
          <div class="quantity-select">
            <span>数量:</span>
            <el-input-number v-model="quantity" :min="1" :max="product.stock" />
          </div>
          
          <el-button type="primary" size="large" @click="addToCart">加入购物车</el-button>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getProductDetail } from '../api'
import { useCartStore } from '../stores/cart'

const route = useRoute()
const cartStore = useCartStore()

const product = ref(null)
const loading = ref(false)
const quantity = ref(1)

const loadProduct = async () => {
  loading.value = true
  const res = await getProductDetail(route.params.id)
  if (res.code === 200) {
    product.value = res.data
  }
  loading.value = false
}

const addToCart = async () => {
  await cartStore.addItem(product.value.id, quantity.value)
  ElMessage.success('已添加到购物车')
}

onMounted(() => {
  loadProduct()
})
</script>

<style scoped>
.product-detail { padding: 40px; max-width: 1200px; margin: 0 auto; }
.main-image { width: 100%; height: 400px; object-fit: cover; border-radius: 8px; }
.description { color: #666; margin: 20px 0; }
.price { font-size: 32px; color: #f56c6c; font-weight: bold; }
.stock { color: #909399; margin: 10px 0; }
.expiry { color: #e6a23c; }
.quantity-select { margin: 20px 0; display: flex; align-items: center; gap: 10px; }
</style>
