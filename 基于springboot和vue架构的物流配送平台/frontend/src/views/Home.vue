<template>
  <div class="home">
    <div class="header">
      <h1>物流配送平台</h1>
      <div class="actions">
        <el-button v-if="authStore.isAdmin" type="danger" @click="$router.push('/admin')">
          管理后台
        </el-button>
        <el-button v-if="authStore.isAuthenticated" @click="$router.push('/order/create')">
          创建订单
        </el-button>
        <el-button v-if="authStore.isAuthenticated" @click="$router.push('/orders')">
          我的订单
        </el-button>
        <el-button v-if="!authStore.isAuthenticated" @click="$router.push('/login')">
          登录
        </el-button>
        <el-button v-if="authStore.isAuthenticated" @click="authStore.logout">
          退出
        </el-button>
      </div>
    </div>
    
    <div class="content">
      <el-card>
        <h2>欢迎使用物流配送平台</h2>
        <p>提供专业的物流配送管理服务</p>
        <el-row :gutter="20" style="margin-top: 40px">
          <el-col :span="8">
            <el-statistic title="今日订单" :value="123" />
          </el-col>
          <el-col :span="8">
            <el-statistic title="配送中" :value="45" />
          </el-col>
          <el-col :span="8">
            <el-statistic title="已完成" :value="856" />
          </el-col>
        </el-row>
      </el-card>
    </div>
  </div>
</template>

<script setup>
import { useAuthStore } from '../stores/auth'

const authStore = useAuthStore()
</script>

<style scoped>
.home {
  min-height: 100vh;
}
.header {
  background: #409eff;
  color: white;
  padding: 20px 40px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.header h1 {
  margin: 0;
}
.actions {
  display: flex;
  gap: 10px;
}
.content {
  padding: 40px;
  max-width: 1200px;
  margin: 0 auto;
}
</style>
