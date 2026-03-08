import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { cartAPI } from '../api'

export const useCartStore = defineStore('cart', () => {
  const items = ref([])
  const loading = ref(false)

  const totalItems = computed(() => {
    return items.value.reduce((sum, item) => sum + item.quantity, 0)
  })

  const totalPrice = computed(() => {
    return items.value.reduce((sum, item) => sum + item.price * item.quantity, 0)
  })

  async function fetchCart() {
    loading.value = true
    try {
      const res = await cartAPI.getCart()
      items.value = res.items || []
    } catch (error) {
      console.error('获取购物车失败:', error)
    } finally {
      loading.value = false
    }
  }

  async function addItem(productId, quantity = 1) {
    try {
      await cartAPI.addItem({ productId, quantity })
      await fetchCart()
      return { success: true }
    } catch (error) {
      return { success: false, message: error.response?.data?.message || '添加失败' }
    }
  }

  async function updateQuantity(itemId, quantity) {
    try {
      await cartAPI.updateItem(itemId, { quantity })
      const item = items.value.find(i => i.id === itemId)
      if (item) item.quantity = quantity
      return { success: true }
    } catch (error) {
      return { success: false, message: '更新失败' }
    }
  }

  async function removeItem(itemId) {
    try {
      await cartAPI.removeItem(itemId)
      items.value = items.value.filter(i => i.id !== itemId)
      return { success: true }
    } catch (error) {
      return { success: false, message: '删除失败' }
    }
  }

  async function clearCart() {
    try {
      await cartAPI.clearCart()
      items.value = []
      return { success: true }
    } catch (error) {
      return { success: false, message: '清空失败' }
    }
  }

  return {
    items,
    loading,
    totalItems,
    totalPrice,
    fetchCart,
    addItem,
    updateQuantity,
    removeItem,
    clearCart
  }
})
