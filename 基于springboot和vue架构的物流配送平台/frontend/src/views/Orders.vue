<template>
  <div class="orders-page">
    <h2>我的订单</h2>
    <el-table :data="orders" stripe style="margin-top: 20px">
      <el-table-column prop="orderNo" label="订单号" width="200"></el-table-column>
      <el-table-column prop="senderName" label="发货人"></el-table-column>
      <el-table-column prop="receiverName" label="收货人"></el-table-column>
      <el-table-column prop="status" label="状态">
        <template #default="{ row }">
          <el-tag :type="getStatusType(row.status)">{{ getStatusText(row.status) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间"></el-table-column>
    </el-table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { orderAPI } from '../api'
import { ElMessage } from 'element-plus'

const orders = ref([])

const statusMap = {
  PENDING: { text: '待接单', type: 'warning' },
  ACCEPTED: { text: '已接单', type: 'info' },
  DELIVERING: { text: '配送中', type: 'primary' },
  COMPLETED: { text: '已完成', type: 'success' },
  CANCELLED: { text: '已取消', type: 'danger' }
}

const getStatusType = (status) => statusMap[status]?.type || 'info'
const getStatusText = (status) => statusMap[status]?.text || status

const loadOrders = async () => {
  try {
    const res = await orderAPI.myOrders()
    orders.value = res.data || []
  } catch (error) {
    ElMessage.error('加载订单失败')
  }
}

onMounted(() => {
  loadOrders()
})
</script>

<style scoped>
.orders-page {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}
</style>
