<template>
  <div class="order-management">
    <el-table :data="orders" stripe>
      <el-table-column prop="orderNo" label="订单号" width="180"></el-table-column>
      <el-table-column prop="senderName" label="发货人"></el-table-column>
      <el-table-column prop="receiverName" label="收货人"></el-table-column>
      <el-table-column prop="status" label="状态">
        <template #default="{ row }">
          <el-tag :type="getStatusType(row.status)">{{ getStatusText(row.status) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间"></el-table-column>
      <el-table-column label="操作">
        <template #default="{ row }">
          <el-button v-if="row.status === 'PENDING'" size="small" type="primary" @click="acceptOrder(row)">接单</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { orderAPI, deliveryAPI } from '../../api'
import { ElMessage } from 'element-plus'

const orders = ref([])
const statusMap = { PENDING: { text: '待接单', type: 'warning' }, ACCEPTED: { text: '已接单', type: 'info' }, DELIVERING: { text: '配送中', type: 'primary' }, COMPLETED: { text: '已完成', type: 'success' }, CANCELLED: { text: '已取消', type: 'danger' } }

const getStatusType = (s) => statusMap[s]?.type || 'info'
const getStatusText = (s) => statusMap[s]?.text || s

const loadOrders = async () => {
  const res = await orderAPI.list()
  orders.value = res.data || []
}

const acceptOrder = async (row) => {
  try {
    await deliveryAPI.accept({ orderId: row.id, courierId: 1, vehicleId: 1 })
    ElMessage.success('接单成功')
    loadOrders()
  } catch (e) {
    ElMessage.error('接单失败')
  }
}

onMounted(() => { loadOrders() })
</script>

<style scoped>
.order-management { padding: 20px; }
</style>
