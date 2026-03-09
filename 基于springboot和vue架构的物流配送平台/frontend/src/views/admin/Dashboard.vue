<template>
  <div class="dashboard">
    <el-row :gutter="20">
      <el-col :span="6"><el-card><div class="stat-card"><el-icon :size="30" color="#409eff"><Document /></el-icon><div><div class="stat-value">{{ stats.orderCount }}</div><div class="stat-label">订单总数</div></div></div></el-card></el-col>
      <el-col :span="6"><el-card><div class="stat-card"><el-icon :size="30" color="#67c23a"><Van /></el-icon><div><div class="stat-value">{{ stats.vehicleCount }}</div><div class="stat-label">车辆总数</div></div></div></el-card></el-col>
      <el-col :span="6"><el-card><div class="stat-card"><el-icon :size="30" color="#e6a23c"><Clock /></el-icon><div><div class="stat-value">{{ stats.pendingCount }}</div><div class="stat-label">待配送</div></div></div></el-card></el-col>
      <el-col :span="6"><el-card><div class="stat-card"><el-icon :size="30" color="#f56c6c"><CircleCheck /></el-icon><div><div class="stat-value">{{ stats.completedCount }}</div><div class="stat-label">已完成</div></div></div></el-card></el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { Document, Van, Clock, CircleCheck } from '@element-plus/icons-vue'

const stats = ref({ orderCount: 156, vehicleCount: 12, pendingCount: 23, completedCount: 98 })
</script>

<style scoped>
.dashboard { padding: 20px; }
.stat-card { display: flex; align-items: center; gap: 15px; }
.stat-value { font-size: 28px; font-weight: bold; }
.stat-label { font-size: 14px; color: #909399; }
</style>
