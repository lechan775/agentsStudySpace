<template>
  <header class="header">
    <div class="header-container">
      <div class="logo" @click="$router.push('/')">
        <el-icon :size="28"><Shop /></el-icon>
        <span>生鲜商城</span>
      </div>
      
      <div class="search-bar">
        <el-input
          v-model="searchKeyword"
          placeholder="搜索商品..."
          @keyup.enter="handleSearch"
        >
          <template #append>
            <el-button :icon="Search" @click="handleSearch" />
          </template>
        </el-input>
      </div>

      <div class="nav-actions">
        <el-badge :value="cartStore.totalItems" :hidden="!cartStore.totalItems">
          <el-button :icon="ShoppingCart" @click="$router.push('/cart')">
            购物车
          </el-button>
        </el-badge>
        
        <template v-if="authStore.isAuthenticated">
          <el-button v-if="authStore.isAdmin" type="danger" @click="$router.push('/admin')">
            管理后台
          </el-button>
          <el-dropdown @command="handleCommand">
            <el-button :icon="User">
              {{ authStore.user?.username || '用户' }}
            </el-button>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="user">个人中心</el-dropdown-item>
                <el-dropdown-item command="orders">我的订单</el-dropdown-item>
                <el-dropdown-item divided command="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </template>
        <template v-else>
          <el-button @click="$router.push('/login')">登录</el-button>
          <el-button type="primary" @click="$router.push('/register')">注册</el-button>
        </template>
      </div>
    </div>
  </header>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { Search, ShoppingCart, User, Shop } from '@element-plus/icons-vue'
import { useAuthStore } from '../stores/auth'
import { useCartStore } from '../stores/cart'

const router = useRouter()
const authStore = useAuthStore()
const cartStore = useCartStore()

const searchKeyword = ref('')

const handleSearch = () => {
  router.push({ path: '/', query: { search: searchKeyword.value } })
}

const handleCommand = (command) => {
  if (command === 'logout') {
    authStore.logout()
  } else if (command === 'user') {
    router.push('/user')
  } else if (command === 'orders') {
    router.push('/orders')
  }
}
</script>

<style scoped>
.header {
  background: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  padding: 0 20px;
}

.header-container {
  max-width: 1200px;
  margin: 0 auto;
  height: 60px;
  display: flex;
  align-items: center;
  gap: 30px;
}

.logo {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 20px;
  font-weight: bold;
  color: #67c23a;
  cursor: pointer;
}

.search-bar {
  flex: 1;
  max-width: 500px;
}

.nav-actions {
  display: flex;
  align-items: center;
  gap: 12px;
}
</style>
