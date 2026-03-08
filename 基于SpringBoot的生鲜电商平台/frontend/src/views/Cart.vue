<template>
  <div class="cart-page">
    <el-card>
      <template #header>
        <h2>购物车</h2>
      </template>
      
      <el-empty v-if="!cartStore.items.length" description="购物车是空的">
        <el-button type="primary" @click="$router.push('/')">去购物</el-button>
      </el-empty>
      
      <template v-else>
        <el-table :data="cartStore.items" style="width: 100%">
          <el-table-column label="商品信息" min-width="300">
            <template #default="{ row }">
              <div class="product-cell">
                <img 
                  :src="row.image || '/placeholder.png'" 
                  class="product-image"
                />
                <div class="product-info">
                  <span>{{ row.name }}</span>
                  <span class="price">¥{{ row.price }}</span>
                </div>
              </div>
            </template>
          </el-table-column>
          
          <el-table-column label="数量" width="180">
            <template #default="{ row }">
              <el-input-number
                v-model="row.quantity"
                :min="1"
                :max="row.stock"
                @change="handleQuantityChange(row.id, row.quantity)"
              />
            </template>
          </el-table-column>
          
          <el-table-column label="小计" width="120">
            <template #default="{ row }">
              <span class="subtotal">¥{{ (row.price * row.quantity).toFixed(2) }}</span>
            </template>
          </el-table-column>
          
          <el-table-column label="操作" width="100">
            <template #default="{ row }">
              <el-button 
                type="danger" 
                text 
                @click="handleRemove(row.id)"
              >
                删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        
        <div class="cart-footer">
          <div class="cart-summary">
            <span>共计 {{ cartStore.totalItems }} 件商品</span>
            <span class="total-price">
              总计：<strong>¥{{ cartStore.totalPrice.toFixed(2) }}</strong>
            </span>
          </div>
          <div class="cart-actions">
            <el-button @click="$router.push('/')">继续购物</el-button>
            <el-button 
              type="primary" 
              size="large"
              :loading="submitting"
              @click="handleSubmitOrder"
            >
              提交订单
            </el-button>
          </div>
        </div>
      </template>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useCartStore } from '../stores/cart'
import { orderAPI } from '../api'
import { ElMessage, ElMessageBox } from 'element-plus'

const router = useRouter()
const cartStore = useCartStore()
const submitting = ref(false)

const handleQuantityChange = async (itemId, quantity) => {
  await cartStore.updateQuantity(itemId, quantity)
}

const handleRemove = async (itemId) => {
  try {
    await ElMessageBox.confirm('确定要删除该商品吗？', '提示')
    await cartStore.removeItem(itemId)
    ElMessage.success('已删除')
  } catch {
    // User cancelled
  }
}

const handleSubmitOrder = async () => {
  submitting.value = true
  try {
    const items = cartStore.items.map(item => ({
      productId: item.productId || item.id,
      quantity: item.quantity
    }))
    
    const res = await orderAPI.create({ items })
    await cartStore.clearCart()
    ElMessage.success('订单创建成功')
    router.push('/orders')
  } catch (error) {
    ElMessage.error(error.response?.data?.message || '订单创建失败')
  } finally {
    submitting.value = false
  }
}

onMounted(() => {
  cartStore.fetchCart()
})
</script>

<style scoped>
.cart-page {
  max-width: 1000px;
  margin: 0 auto;
}

.cart-page h2 {
  margin: 0;
}

.product-cell {
  display: flex;
  align-items: center;
  gap: 15px;
}

.product-image {
  width: 80px;
  height: 80px;
  object-fit: cover;
  border-radius: 4px;
}

.product-info {
  display: flex;
  flex-direction: column;
  gap: 5px;
}

.product-info .price {
  color: #f56c6c;
}

.subtotal {
  font-size: 18px;
  font-weight: bold;
  color: #f56c6c;
}

.cart-footer {
  margin-top: 20px;
  padding-top: 20px;
  border-top: 1px solid #eee;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.cart-summary {
  display: flex;
  flex-direction: column;
  gap: 5px;
}

.total-price {
  font-size: 20px;
}

.total-price strong {
  color: #f56c6c;
  font-size: 24px;
}

.cart-actions {
  display: flex;
  gap: 15px;
}
</style>
