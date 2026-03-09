import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { authAPI } from '../api'
import router from '../router'

// Safe JSON parse helper
function safeParseJSON(value) {
  try {
    return value ? JSON.parse(value) : null
  } catch {
    return null
  }
}

export const useAuthStore = defineStore('auth', () => {
  const token = ref(localStorage.getItem('token') || '')
  const user = ref(safeParseJSON(localStorage.getItem('user')))

  const isAuthenticated = computed(() => !!token.value)
  const isAdmin = computed(() => user.value?.role === 'ADMIN')

  async function login(credentials) {
    try {
      const res = await authAPI.login(credentials)
      token.value = res.token
      user.value = res.user
      localStorage.setItem('token', res.token)
      localStorage.setItem('user', JSON.stringify(res.user))
      router.push('/')
      return { success: true }
    } catch (error) {
      return { success: false, message: error.response?.data?.message || '登录失败' }
    }
  }

  async function register(userData) {
    try {
      const res = await authAPI.register(userData)
      token.value = res.token
      user.value = res.user
      localStorage.setItem('token', res.token)
      localStorage.setItem('user', JSON.stringify(res.user))
      router.push('/')
      return { success: true }
    } catch (error) {
      return { success: false, message: error.response?.data?.message || '注册失败' }
    }
  }

  function logout() {
    token.value = ''
    user.value = null
    localStorage.removeItem('token')
    localStorage.removeItem('user')
    router.push('/login')
  }

  return {
    token,
    user,
    isAuthenticated,
    isAdmin,
    login,
    register,
    logout
  }
})
