<template>
  <div class="admin-layout">
    <el-container>
      <el-aside width="200px">
        <div class="logo">管理后台</div>
        <el-menu
          :default-active="activeMenu"
          router
          class="admin-menu"
        >
          <el-menu-item index="/admin/dashboard">
            <el-icon><DataAnalysis /></el-icon>
            <span>数据统计</span>
          </el-menu-item>
          <el-menu-item index="/admin/products">
            <el-icon><Goods /></el-icon>
            <span>商品管理</span>
          </el-menu-item>
          <el-menu-item index="/admin/categories">
            <el-icon><Grid /></el-icon>
            <span>分类管理</span>
          </el-menu-item>
          <el-menu-item index="/admin/orders">
            <el-icon><Document /></el-icon>
            <span>订单管理</span>
          </el-menu-item>
          <el-menu-item index="/admin/users">
            <el-icon><User /></el-icon>
            <span>用户管理</span>
          </el-menu-item>
          <el-menu-item index="/" divided>
            <el-icon><Back /></el-icon>
            <span>返回前台</span>
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-container>
        <el-header>
          <div class="header-right">
            <span>管理员</span>
            <el-button @click="logout">退出</el-button>
          </div>
        </el-header>
        <el-main>
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { DataAnalysis, Goods, Grid, Document, User, Back } from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()
const activeMenu = ref(route.path)

watch(() => route.path, (newPath) => {
  if (newPath.startsWith('/admin')) {
    activeMenu.value = newPath
  }
})

const logout = () => {
  router.push('/')
}
</script>

<style scoped>
.admin-layout {
  height: 100vh;
}
.el-container {
  height: 100%;
}
.el-aside {
  background: #304156;
}
.logo {
  height: 60px;
  line-height: 60px;
  text-align: center;
  color: #fff;
  font-size: 18px;
  font-weight: bold;
  background: #2b3a4a;
}
.admin-menu {
  border: none;
  background: #304156;
}
.admin-menu .el-menu-item {
  color: #bfcbd9;
}
.admin-menu .el-menu-item:hover,
.admin-menu .el-menu-item.is-active {
  background: #263445 !important;
  color: #409eff !important;
}
.el-header {
  background: #fff;
  box-shadow: 0 1px 4px rgba(0,0,0,0.1);
  display: flex;
  align-items: center;
  justify-content: flex-end;
  padding: 0 20px;
}
.header-right {
  display: flex;
  align-items: center;
  gap: 15px;
}
.el-main {
  background: #f0f2f5;
}
</style>
