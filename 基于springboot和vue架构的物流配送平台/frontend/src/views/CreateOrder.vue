<template>
  <div class="create-order">
    <el-card>
      <h2>创建订单</h2>
      <el-form :model="form" label-width="100px">
        <el-form-item label="发货人"><el-input v-model="form.senderName" /></el-form-item>
        <el-form-item label="发货电话"><el-input v-model="form.senderPhone" /></el-form-item>
        <el-form-item label="发货地址"><el-input v-model="form.senderAddress" /></el-form-item>
        <el-form-item label="收货人"><el-input v-model="form.receiverName" /></el-form-item>
        <el-form-item label="收货电话"><el-input v-model="form.receiverPhone" /></el-form-item>
        <el-form-item label="收货地址"><el-input v-model="form.receiverAddress" /></el-form-item>
        <el-form-item label="货物描述"><el-input v-model="form.goodsDescription" /></el-form-item>
        <el-form-item label="重量(kg)"><el-input-number v-model="form.weight" :min="0" /></el-form-item>
        <el-form-item label="价格"><el-input-number v-model="form.price" :min="0" :precision="2" /></el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitOrder">提交订单</el-button>
          <el-button @click="$router.back()">返回</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { orderAPI } from '../api'
import { ElMessage } from 'element-plus'

const router = useRouter()
const form = ref({ senderName: '', senderPhone: '', senderAddress: '', receiverName: '', receiverPhone: '', receiverAddress: '', goodsDescription: '', weight: 0, price: 0 })

const submitOrder = async () => {
  try {
    await orderAPI.create(form.value)
    ElMessage.success('订单创建成功')
    router.push('/orders')
  } catch (e) {
    ElMessage.error('创建失败')
  }
}
</script>

<style scoped>
.create-order { padding: 40px; max-width: 800px; margin: 0 auto; }
</style>
