<template>
  <div class="orders-page">
    <el-card>
      <template #header>
        <h2>我的订单</h2>
      </template>
      
      <el-empty v-if="!loading && !orders.length" description="暂无订单">
        <el-button type="primary" @click="$router.push('/')">去购物</el-button>
      </el-empty>
      
      <div v-else class="orders-list">
        <el-card 
          v-for="order in orders" 
          :key="order.id" 
          class="order-card"
        >
          <template #header>
            <div class="order-header">
              <span class="order-no">订单号：{{ order.orderNo }}</span>
              <el-tag :type="getStatusType(order.status)">
                {{ getStatusText(order.status) }}
              </el-tag>
            </div>
          </template>
          
          <div class="order-items">
            <div 
              v-for="item in order.items" 
              :key="item.id" 
              class="order-item"
            >
              <img :src="item.image || '/placeholder.png'" class="item-image" />
              <div class="item-info">
                <span>{{ item.name }}</span>
                <span class="item-price">¥{{ item.price }} × {{ item.quantity }}</span>
              </div>
            </div>
          </div>
          
          <div class="order-footer">
            <span class="order-time">{{ formatTime(order.createdAt) }}</span>
            <span class="order-total">
              合计：<strong>¥{{ order.totalAmount }}</strong>
            </span>
          </div>
        </el-card>
      </div>
      
      <div v-if="total > pageSize" class="pagination">
        <el-pagination
          v-model:current-page="currentPage"
          :page-size="pageSize"
          :total="total"
          layout="prev, pager, next"
          @current-change="fetchOrders"
        />
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { orderAPI } from '../api'
import { ElMessage } from 'element-plus'

const orders = ref([])
const loading = ref(true)
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

const statusMap = {
  pending: { text: '待付款', type: 'warning' },
  paid: { text: '已付款', type: 'success' },
  shipped: { text: '已发货', type: 'success' },
  completed: { text: '已完成', type: 'success' },
  cancelled: { text: '已取消', type: 'info' }
}

const getStatusText = (status) => statusMap[status]?.text || status
const getStatusType = (status) => statusMap[status]?.type || 'info'

const formatTime = (time) => {
  if (!time) return ''
  return new Date(time).toLocaleString('zh-CN')
}

const fetchOrders = async () => {
  loading.value = true
  try {
    const res = await orderAPI.getList({
      page: currentPage.value,
      size: pageSize.value
    })
    orders.value = res.data || res || []
    total.value = res.total || orders.value.length
  } catch (error) {
    console.error('获取订单失败:', error)
    ElMessage.error('获取订单失败')
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  fetchOrders()
})
</script>

<style scoped>
.orders-page {
  max-width: 900px;
  margin: 0 auto;
}

.orders-page h2 {
  margin: 0;
}

.orders-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.order-card {
  margin-bottom: 0;
}

.order-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.order-no {
  color: #666;
}

.order-items {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.order-item {
  display: flex;
  align-items: center;
  gap: 15px;
}

.item-image {
  width: 60px;
  height: 60px;
  object-fit: cover;
  border-radius: 4px;
}

.item-info {
  flex: 1;
  display: flex;
  justify-content: space-between;
}

.item-price {
  color: #666;
}

.order-footer {
  margin-top: 15px;
  padding-top: 15px;
  border-top: 1px solid #eee;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.order-time {
  color: #999;
  font-size: 14px;
}

.order-total {
  font-size: 18px;
}

.order-total strong {
  color: #f56c6c;
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}
</style>
