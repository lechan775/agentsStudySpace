<template>
  <div class="vehicle-management">
    <el-button type="primary" @click="showAddDialog">添加车辆</el-button>
    <el-table :data="vehicles" stripe style="margin-top:20px">
      <el-table-column prop="id" label="ID" width="60"></el-table-column>
      <el-table-column prop="vehicleNumber" label="车牌号"></el-table-column>
      <el-table-column prop="brand" label="品牌"></el-table-column>
      <el-table-column prop="model" label="型号"></el-table-column>
      <el-table-column prop="status" label="状态">
        <template #default="{ row }">
          <el-tag :type="row.status === 'IDLE' ? 'success' : row.status === 'IN_USE' ? 'warning' : 'danger'">{{ row.status }}</el-tag>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog v-model="dialogVisible" title="添加车辆" width="400px">
      <el-form :model="form">
        <el-form-item label="车牌号"><el-input v-model="form.vehicleNumber" /></el-form-item>
        <el-form-item label="品牌"><el-input v-model="form.brand" /></el-form-item>
        <el-form-item label="型号"><el-input v-model="form.model" /></el-form-item>
        <el-form-item label="最大载重"><el-input-number v-model="form.maxLoad" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible=false">取消</el-button>
        <el-button type="primary" @click="saveVehicle">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { vehicleAPI } from '../../api'
import { ElMessage } from 'element-plus'

const vehicles = ref([])
const dialogVisible = ref(false)
const form = ref({ vehicleNumber: '', brand: '', model: '', maxLoad: 1000 })

const loadVehicles = async () => {
  const res = await vehicleAPI.list()
  vehicles.value = res.data || []
}

const showAddDialog = () => { dialogVisible.value = true }

const saveVehicle = async () => {
  await vehicleAPI.create(form.value)
  ElMessage.success('添加成功')
  dialogVisible.value = false
  loadVehicles()
}

onMounted(() => { loadVehicles() })
</script>

<style scoped>
.vehicle-management { padding: 20px; }
</style>
