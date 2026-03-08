<template>
  <div class="user-page">
    <el-card>
      <template #header>
        <h2>个人中心</h2>
      </template>
      
      <el-tabs v-model="activeTab">
        <el-tab-pane label="个人资料" name="profile">
          <el-form 
            ref="profileFormRef"
            :model="profileForm" 
            label-width="100px"
            style="max-width: 500px"
          >
            <el-form-item label="用户名">
              <el-input v-model="profileForm.username" disabled />
            </el-form-item>
            <el-form-item label="邮箱">
              <el-input v-model="profileForm.email" />
            </el-form-item>
            <el-form-item label="手机号">
              <el-input v-model="profileForm.phone" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleUpdateProfile">
                保存修改
              </el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        
        <el-tab-pane label="收货地址" name="address">
          <div class="address-header">
            <el-button type="primary" @click="handleAddAddress">
              新增地址
            </el-button>
          </div>
          
          <el-empty v-if="!addresses.length" description="暂无收货地址" />
          
          <div v-else class="address-list">
            <el-card 
              v-for="address in addresses" 
              :key="address.id" 
              class="address-card"
            >
              <div class="address-content">
                <div class="address-info">
                  <p><strong>{{ address.receiver }}</strong> {{ address.phone }}</p>
                  <p>{{ address.province }} {{ address.city }} {{ address.district }}</p>
                  <p>{{ address.detail }}</p>
                </div>
                <div class="address-actions">
                  <el-button text @click="handleEditAddress(address)">编辑</el-button>
                  <el-button 
                    text 
                    type="danger"
                    @click="handleDeleteAddress(address.id)"
                  >
                    删除
                  </el-button>
                </div>
              </div>
            </el-card>
          </div>
        </el-tab-pane>
      </el-tabs>
    </el-card>
    
    <!-- Address Dialog -->
    <el-dialog 
      v-model="addressDialogVisible" 
      :title="editingAddress ? '编辑地址' : '新增地址'"
      width="500px"
    >
      <el-form 
        ref="addressFormRef"
        :model="addressForm" 
        :rules="addressRules"
        label-width="80px"
      >
        <el-form-item label="收货人" prop="receiver">
          <el-input v-model="addressForm.receiver" />
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="addressForm.phone" />
        </el-form-item>
        <el-form-item label="省份" prop="province">
          <el-input v-model="addressForm.province" />
        </el-form-item>
        <el-form-item label="城市" prop="city">
          <el-input v-model="addressForm.city" />
        </el-form-item>
        <el-form-item label="区县" prop="district">
          <el-input v-model="addressForm.district" />
        </el-form-item>
        <el-form-item label="详细地址" prop="detail">
          <el-input v-model="addressForm.detail" type="textarea" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="addressDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSaveAddress">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useAuthStore } from '../stores/auth'
import { userAPI } from '../api'
import { ElMessage, ElMessageBox } from 'element-plus'

const authStore = useAuthStore()

const activeTab = ref('profile')
const profileFormRef = ref()
const addressFormRef = ref()

const profileForm = reactive({
  username: '',
  email: '',
  phone: ''
})

const addresses = ref([])
const addressDialogVisible = ref(false)
const editingAddress = ref(null)

const addressForm = reactive({
  receiver: '',
  phone: '',
  province: '',
  city: '',
  district: '',
  detail: ''
})

const addressRules = {
  receiver: [{ required: true, message: '请输入收货人', trigger: 'blur' }],
  phone: [{ required: true, message: '请输入手机号', trigger: 'blur' }],
  province: [{ required: true, message: '请输入省份', trigger: 'blur' }],
  city: [{ required: true, message: '请输入城市', trigger: 'blur' }],
  detail: [{ required: true, message: '请输入详细地址', trigger: 'blur' }]
}

const fetchProfile = async () => {
  try {
    const res = await userAPI.getProfile()
    const user = res.data || res
    profileForm.username = user.username
    profileForm.email = user.email
    profileForm.phone = user.phone
  } catch (error) {
    console.error('获取个人信息失败:', error)
  }
}

const handleUpdateProfile = async () => {
  try {
    await userAPI.updateProfile({
      email: profileForm.email,
      phone: profileForm.phone
    })
    ElMessage.success('保存成功')
  } catch (error) {
    ElMessage.error('保存失败')
  }
}

const fetchAddresses = async () => {
  try {
    const res = await userAPI.getAddresses()
    addresses.value = res.data || res || []
  } catch (error) {
    console.error('获取地址失败:', error)
  }
}

const handleAddAddress = () => {
  editingAddress.value = null
  Object.assign(addressForm, {
    receiver: '',
    phone: '',
    province: '',
    city: '',
    district: '',
    detail: ''
  })
  addressDialogVisible.value = true
}

const handleEditAddress = (address) => {
  editingAddress.value = address
  Object.assign(addressForm, address)
  addressDialogVisible.value = true
}

const handleSaveAddress = async () => {
  const valid = await addressFormRef.value.validate().catch(() => false)
  if (!valid) return
  
  try {
    if (editingAddress.value) {
      await userAPI.updateAddress(editingAddress.value.id, addressForm)
    } else {
      await userAPI.addAddress(addressForm)
    }
    ElMessage.success('保存成功')
    addressDialogVisible.value = false
    fetchAddresses()
  } catch (error) {
    ElMessage.error('保存失败')
  }
}

const handleDeleteAddress = async (id) => {
  try {
    await ElMessageBox.confirm('确定要删除该地址吗？', '提示')
    await userAPI.deleteAddress(id)
    ElMessage.success('删除成功')
    fetchAddresses()
  } catch {
    // User cancelled
  }
}

onMounted(() => {
  fetchProfile()
  fetchAddresses()
})
</script>

<style scoped>
.user-page {
  max-width: 800px;
  margin: 0 auto;
}

.user-page h2 {
  margin: 0;
}

.address-header {
  margin-bottom: 20px;
}

.address-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.address-card {
  margin-bottom: 0;
}

.address-content {
  display: flex;
  justify-content: space-between;
}

.address-info p {
  margin: 5px 0;
}

.address-actions {
  display: flex;
  gap: 10px;
}
</style>
