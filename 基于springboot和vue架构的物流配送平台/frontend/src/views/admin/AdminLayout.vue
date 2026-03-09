<template>
  <div class="admin-layout">
    <el-container>
      <el-aside width="200px">
        <div class="logo">管理后台</div>
        <el-menu :default-active="activeMenu" router class="admin-menu">
          <el-menu-item index="/admin/dashboard">
            <el-icon><DataAnalysis /></el-icon>
            <span>数据统计</span>
          </el-menu-item>
          <el-menu-item index="/admin/orders">
            <el-icon><Document /></el-icon>
            <span>订单管理</span>
          </el-menu-item>
          <el-menu-item index="/admin/vehicles">
            <el-icon><Van /></el-icon>
            <span>车辆管理</span>
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-container>
        <el-header>
          <div class="header-right">
            <el-button @click="logout">退出</el-button>
          </div>
        </el-header>
        <el-main><router-view /></el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { DataAnalysis, Document, Van } from '@element-plus/icons-vue'
import { useAuthStore } from '../../stores/auth'

const router = useRouter()
const route = useRoute()
const authStore = useAuthStore()
const activeMenu = ref(route.path)

const logout = () => authStore.logout()
</script>

<style scoped>
.admin-layout { height: 100vh; }
.el-aside { background: #304156; }
.logo { height: 60px; line-height: 60px; text-align: center; color: #fff; font-size: 18px; font-weight: bold; background: #2b3a4a; }
.admin-menu { border: none; background: #304156; }
.admin-menu .el-menu-item { color: #bfcbd9; }
.admin-menu .el-menu-item:hover, .admin-menu .el-menu-item.is-active { background: #263445 !important; color: #409eff !important; }
.el-header { background: #fff; box-shadow: 0 1px 4px rgba(0,0,0,0.1); display: flex; align-items: center; justify-content: flex-end; padding: 0 20px; }
.el-main { background: #f0f2f5; }
</style>
