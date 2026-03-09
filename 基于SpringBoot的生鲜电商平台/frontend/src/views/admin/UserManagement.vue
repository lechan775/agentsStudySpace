<template>
  <div class="user-management">
    <div class="toolbar">
      <el-input v-model="searchKeyword" placeholder="搜索用户" style="width: 300px">
        <template #append>
          <el-button @click="loadUsers">搜索</el-button>
        </template>
      </el-input>
    </div>

    <el-table :data="users" stripe style="margin-top: 20px">
      <el-table-column prop="id" label="ID" width="60"></el-table-column>
      <el-table-column prop="username" label="用户名" width="120"></el-table-column>
      <el-table-column prop="email" label="邮箱" width="180"></el-table-column>
      <el-table-column prop="phone" label="手机号" width="120"></el-table-column>
      <el-table-column prop="nickname" label="昵称" width="120"></el-table-column>
      <el-table-column prop="role" label="角色" width="80">
        <template #default="{ row }">
          <el-tag :type="row.role === 'ADMIN' ? 'danger' : 'success'">{{ row.role }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="status" label="状态" width="80">
        <template #default="{ row }">
          <el-tag :type="row.status === 'ACTIVE' ? 'success' : 'danger'">{{ row.status }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="注册时间" width="170"></el-table-column>
      <el-table-column label="操作" width="150">
        <template #default="{ row }">
          <el-button size="small" type="warning" @click="toggleStatus(row)">
            {{ row.status === 'ACTIVE' ? '禁用' : '启用' }}
          </el-button>
          <el-button size="small" type="danger" @click="deleteUser(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      v-model:current-page="currentPage"
      :page-size="pageSize"
      :total="total"
      layout="total, prev, pager, next"
      style="margin-top: 20px; justify-content: flex-end"
      @current-change="loadUsers"
    />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'

const users = ref([])
const searchKeyword = ref('')
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

const loadUsers = () => {
  users.value = [
    { id: 1, username: 'zhangsan', email: 'zhangsan@example.com', phone: '13800138000', nickname: '张三', role: 'USER', status: 'ACTIVE', createTime: '2026-01-15 10:30:00' },
    { id: 2, username: 'lisi', email: 'lisi@example.com', phone: '13800138001', nickname: '李四', role: 'USER', status: 'ACTIVE', createTime: '2026-02-01 14:20:00' },
    { id: 3, username: 'wangwu', email: 'wangwu@example.com', phone: '13800138002', nickname: '王五', role: 'USER', status: 'BANNED', createTime: '2026-02-10 09:15:00' },
    { id: 4, username: 'admin', email: 'admin@example.com', phone: '13900139000', nickname: '管理员', role: 'ADMIN', status: 'ACTIVE', createTime: '2026-01-01 08:00:00' }
  ]
  total.value = 4
}

const toggleStatus = (row) => {
  row.status = row.status === 'ACTIVE' ? 'BANNED' : 'ACTIVE'
  ElMessage.success(`用户已${row.status === 'ACTIVE' ? '启用' : '禁用'}`)
}

const deleteUser = async (row) => {
  try {
    await ElMessageBox.confirm('确定要删除该用户吗？', '提示', { type: 'warning' })
    users.value = users.value.filter(u => u.id !== row.id)
    ElMessage.success('删除成功')
  } catch {}
}

onMounted(() => {
  loadUsers()
})
</script>

<style scoped>
.user-management {
  padding: 20px;
}
.toolbar {
  display: flex;
  align-items: center;
}
</style>
