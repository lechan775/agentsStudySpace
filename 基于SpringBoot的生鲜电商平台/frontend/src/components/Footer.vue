<template>
  <footer class="footer">
    <div class="footer-container">
      <div class="footer-section">
        <h4>关于我们</h4>
        <p>新鲜直达，品质保障</p>
        <p>致力于为您提供最新鲜的生鲜产品</p>
      </div>
      <div class="footer-section">
        <h4>联系方式</h4>
        <p>客服热线：400-888-8888</p>
        <p>工作时间：9:00-21:00</p>
      </div>
      <div class="footer-section">
        <h4>关注我们</h4>
        <div class="social-links">
          <el-icon><Link /></el-icon>
          <span>官方微信</span>
        </div>
      </div>
    </div>
    <div class="footer-bottom">
      © 2024 生鲜电商平台. All rights reserved.
    </div>
  </footer>
</template>

<script setup>
import { Link } from '@element-plus/icons-vue'
</script>

<style scoped>
.footer {
  background: #2c3e50;
  color: #fff;
  margin-top: auto;
}

.footer-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 30px 20px;
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 30px;
}

.footer-section h4 {
  margin-bottom: 15px;
  font-size: 16px;
}

.footer-section p {
  margin: 8px 0;
  color: #a0aec0;
  font-size: 14px;
}

.social-links {
  display: flex;
  align-items: center;
  gap: 8px;
  color: #a0aec0;
}

.footer-bottom {
  text-align: center;
  padding: 20px;
  border-top: 1px solid #3d4a5c;
  color: #a0aec0;
  font-size: 14px;
}
</style>
