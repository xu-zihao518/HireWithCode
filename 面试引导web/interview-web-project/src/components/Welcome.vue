<template>
    <div class="welcomepage">
        <img src="../assets/logo.png" alt="无限状态公司logo" class="logo" />
        <h1 class="welcome-text">欢迎来到 无限状态 线上面试环节，期待你的加入！</h1>
    </div>
</template>

<script setup>
import { useRouter } from 'vue-router' 
import { onMounted } from 'vue'
// 两秒后跳转
onMounted(() => {
  const router = useRouter()
  setTimeout(() => {
    router.push('/interviewguide')
  }, 2000)
})
</script>

<style lang="scss" scoped>
.welcomepage{
    width: 100vw;
    height: 100vh;
    display: flex;
    flex-direction: column; 
    justify-content: center; 
    align-items: center; 
    background-color: #f8f9fa;
    padding: 20px;
    box-sizing: border-box;
}

.logo {
    
    width: clamp(180px, 40vw, 400px);
    height: auto; 
    object-fit: contain; 
    margin-bottom: 40px; 
    filter: drop-shadow(0 4px 8px rgba(0,0,0,0.08));
}

.welcome-text {
    text-align: center;
    font-size: clamp(18px, 3vw, 28px);
    font-weight: 600;
    color: #333;
    line-height: 1.6;
    max-width: 800px;
    letter-spacing: 0.5px;
}

// 移动端适配（屏幕宽度≤768px时）
@media (max-width: 768px) {
    .logo {
        margin-bottom: 30px; // 减小间距
    }
    
    .welcome-text {
        padding: 0 15px; // 左右留白
        font-size: clamp(16px, 5vw, 22px); // 调整字体适配
    }
}
</style>