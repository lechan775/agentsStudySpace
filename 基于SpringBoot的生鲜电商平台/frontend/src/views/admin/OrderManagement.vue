<template>
  <div class="order-management">
    <div class="toolbar">
      <el-select v-model="statusFilter" placeholder="订单状态" clearable style="margin-right: 10px">
        <el-option label="全部" :value="null"></el-option>
        <el-option label="待付款" value="PENDING_PAYMENT"></el-option>
        <el-option label="已付款" value="PAID"></el-option>
        <el-option label="已发货" value="SHIPPED"></el-option>
        <el-option label="已完成" value="COMPLETED"></el-option>
        <el-option label="已取消" value="CANCELLED"></el-option>
      </el-select>
      <el-button @click="loadOrders">筛选</el-button>
    </div>

    <el-table :data="orders" stripe style="margin-top: 20px">
      <el-table-column prop="orderNo" label="订单号" width="180"></el-table-column>
      <el-table-column prop="user.username" label="用户" width="100"></el-table-column>
      <el-table-column prop="totalAmount" label="订单金额" width="100">
        <template #default="{ row }">¥{{ row.totalAmount }}</template>
      </el-table-column>
      <el-table-column prop="payAmount" label="实付金额" width="100">
        <template #default="{ row }">¥{{ row.payAmount }}</template>
      </el-table-column>
      <el-table-column prop="status" label="状态" width="100">
        <template #default="{ row }">
          <el-tag :type="getStatusType(row.status)">{{ getStatusText(row.status) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="下单时间" width="170"></el-table-column>
      <el-table-column label="操作" fixed="right" width="180">
        <template #default="{ row }">
          <el-button size="small" @click="viewDetail(row)">详情</el-button>
          <el-button v-if="row.status === 'PAID'" size="small" type="primary" @click="shipOrder(row)">
            发货
          </el-button>
          <el-button v-if="row.status !== 'COMPLETED' && row.status !== 'CANCELLED'" size="small" type="danger" @click="cancelOrder(row)">
            取消
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      v-model:current-page="currentPage"
      :page-size="pageSize"
      :total="total"
      layout="total, prev, pager, next"
      style="margin-top: 20px; justify-content: flex-end"
      @current-change="loadOrders"
    />

    <!-- Order Detail Dialog -->
    <el-dialog v-model="detailVisible" title="订单详情" width="600px">
      <el-descriptions :column="2" border v-if="currentOrder">
        <el-descriptions-item label="订单号">{{ currentOrder.orderNo }}</el-descriptions-item>
        <el-descriptions-item label="用户">{{ currentOrder.user?.username }}</el-descriptions-item>
        <el-descriptions-item label="订单金额">¥{{ currentOrder.totalAmount }}</el-descriptions-item>
        <el-descriptions-item label="实付金额">¥{{ currentOrder.payAmount }}</el-descriptions-item>
        <el-descriptions-item label="订单状态">{{ getStatusText(currentOrder.status) }}</el-descriptions-item>
        <el-descriptions-item label="下单时间">{{ currentOrder.createTime }}</el-descriptions-item>
      </el-descriptions>
      <el-table :data="currentOrder?.items || []" style="margin-top: 20px" stripe>
        <el-table-column prop="productName" label="商品"></el-table-column>
        <el-table-column prop="price" label="单价">
          <template #default="{ row }">¥{{ row.price }}</template>
        </el-table-column>
        <el-table-column prop="quantity" label="数量"></el-table-column>
        <el-table-column prop="subtotal" label="小计">
          <template #default="{ row }">¥{{ row.subtotal }}</template>
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'

const orders = ref([])
const statusFilter = ref(null)
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const detailVisible = ref(false)
const currentOrder = ref(null)

const statusMap = {
  PENDING_PAYMENT: { text: '待付款', type: 'warning' },
  PAID: { text: '已付款', type: 'info' },
  SHIPPED: { text: '已发货', type: 'info' },
  RECEIVED: { text: '已收货', type: 'success' },
  COMPLETED: { text: '已完成', type: 'success' },
  CANCELLED: { text: '已取消', type: 'danger' }
}

const getStatusType = (status) => statusMap[status]?.type || 'info'
const getStatusText = (status) => statusMap[status]?.text || status

const loadOrders = () => {
  orders.value = [
    { id: 1, orderNo: 'ORDER20260309001', user: { username: '张三' }, totalAmount: 128.5, payAmount: 118.5, status: 'COMPLETED', createTime: '2026-03-09 10:30:00', items: [{ productName: '新鲜草莓', price: 29.9, quantity: 3, subtotal: 89.7 }, { productName: '进口车厘子', price: 89.0, quantity: 1, subtotal: 89.0 }] },
    { id: 2, orderNo: 'ORDER20260309002', user: { username: '李四' }, totalAmount: 89.0, payAmount: 89.0, status: 'SHIPPED', createTime: '2026-03-09 11:20:00', items: [{ productName: '有机西兰花', price: 12.8, quantity: 5, subtotal: 64.0 }] },
    { id: 3, orderNo: 'ORDER20260309003', user: { username: '王五' }, totalAmount: 256.8, payAmount: 246.8, status: 'PAID', createTime: '2026-03-09 14:15:00', items: [] }
  ]
  total.value = 3
}

const viewDetail = (row) => {
  currentOrder.value = row
  detailVisible.value = true
}

const shipOrder = (row) => {
  row.status = 'SHIPPED'
  ElMessage.success('订单已发货')
}

const cancelOrder = (row) => {
  row.status = 'CANCELLED'
  ElMessage.success('订单已取消')
}

onMounted(() => {
  loadOrders()
})
</script>

<style scoped>
.order-management {
  padding: 20px;
}
.toolbar {
  display: flex;
  align-items: center;
}
</style>
