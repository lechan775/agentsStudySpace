<template>
  <div class="dashboard">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <div class="stat-icon" style="background: #409eff">
              <el-icon :size="30"><User /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ stats.userCount }}</div>
              <div class="stat-label">用户总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <div class="stat-icon" style="background: #67c23a">
              <el-icon :size="30"><Goods /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ stats.productCount }}</div>
              <div class="stat-label">商品总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <div class="stat-icon" style="background: #e6a23c">
              <el-icon :size="30"><Document /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ stats.orderCount }}</div>
              <div class="stat-label">订单总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <div class="stat-icon" style="background: #f56c6c">
              <el-icon :size="30"><Money /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">¥{{ stats.totalSales }}</div>
              <div class="stat-label">销售总额</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="12">
        <el-card>
          <template #header>
            <span>最近订单</span>
          </template>
          <el-table :data="recentOrders" stripe>
            <el-table-column prop="orderNo" label="订单号" width="180"></el-table-column>
            <el-table-column prop="user.username" label="用户"></el-table-column>
            <el-table-column prop="payAmount" label="金额">
              <template #default="{ row }">¥{{ row.payAmount }}</template>
            </el-table-column>
            <el-table-column prop="status" label="状态">
              <template #default="{ row }">
                <el-tag :type="getStatusType(row.status)">{{ row.status }}</el-tag>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header>
            <span>热销商品</span>
          </template>
          <el-table :data="hotProducts" stripe>
            <el-table-column prop="name" label="商品名称"></el-table-column>
            <el-table-column prop="salesCount" label="销量"></el-table-column>
            <el-table-column prop="stock" label="库存"></el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { User, Goods, Document, Money } from '@element-plus/icons-vue'

const stats = ref({
  userCount: 0,
  productCount: 0,
  orderCount: 0,
  totalSales: 0
})

const recentOrders = ref([])
const hotProducts = ref([])

const getStatusType = (status) => {
  const map = {
    PENDING_PAYMENT: 'warning',
    PAID: 'info',
    SHIPPED: 'info',
    COMPLETED: 'success',
    CANCELLED: 'danger'
  }
  return map[status] || 'info'
}

onMounted(() => {
  // Mock data
  stats.value = {
    userCount: 128,
    productCount: 86,
    orderCount: 356,
    totalSales: 28560
  }
  recentOrders.value = [
    { id: 1, orderNo: 'ORDER20260309001', user: { username: '张三' }, payAmount: 128.5, status: 'COMPLETED' },
    { id: 2, orderNo: 'ORDER20260309002', user: { username: '李四' }, payAmount: 89.0, status: 'SHIPPED' },
    { id: 3, orderNo: 'ORDER20260309003', user: { username: '王五' }, payAmount: 256.8, status: 'PAID' }
  ]
  hotProducts.value = [
    { id: 1, name: '新鲜草莓', salesCount: 156, stock: 100 },
    { id: 2, name: '进口车厘子', salesCount: 98, stock: 30 },
    { id: 3, name: '有机西兰花', salesCount: 87, stock: 50 }
  ]
})
</script>

<style scoped>
.dashboard {
  padding: 20px;
}
.stat-card {
  display: flex;
  align-items: center;
  gap: 20px;
}
.stat-icon {
  width: 60px;
  height: 60px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
}
.stat-value {
  font-size: 28px;
  font-weight: bold;
  color: #303133;
}
.stat-label {
  font-size: 14px;
  color: #909399;
}
</style>
