<template>
  <transition name="fade">
    <div class="finishchallenge">
      <div class="tabinfomation">
        <span class="acceptspan">完成挑战</span>
        <button class="delect" @click="handleClose">X关闭</button>
      </div>
      <form @submit.prevent="handleSubmit" class="interview-form">
        <div class="formgroup">
          <label for="githubId">GitHub ID <span class="required">*</span></label>
          <input type="text" placeholder="请输入您的github id" v-model="form.githubId">
        </div>
        <div class="formgroup">
          <label for="githubUrl">GitHub 仓库地址 <span class="required">*</span></label>
          <input type="url" placeholder="如：https://github.com/xxx" v-model="form.repoUrl">
        </div>
        <div class="formgroup">
          <label for="vercelUrl">Vercel 在线地址 <span class="required">*</span></label>
          <input type="url" placeholder="如：https://xxx.vercel.app" v-model="form.vercelUrl">
        </div>
        <div class="formgroup">
          <button type="submit">提交</button>
        </div>
      </form>
    </div>
  </transition>
</template>

<script setup>
import { ref } from 'vue'
import { defineEmits } from 'vue'

// 定义关闭事件
const emit = defineEmits(['close'])
const handleClose = () => {
  emit('close')
}

// 表单数据
const form = ref({
  githubId: '',
  repoUrl: '',
  vercelUrl: ''
})

const handleSubmit = async () => {
  // 处理输入值，去除首尾空格
  const githubId = form.value.githubId.trim()
  const repoUrl = form.value.repoUrl.trim()
  const vercelUrl = form.value.vercelUrl.trim()

  // 表单验证
  if (githubId === '') {
    alert('请输入GitHub ID')
    return
  }
  
  if (repoUrl === '') {
    alert('请输入有效的GitHub仓库地址')
    return
  }

  if (vercelUrl === '') {
    alert('请输入有效的Vercel在线地址')
    return
  }

  try {
    // 关键修复：提交时使用与后端数据库匹配的下划线字段名
    const response = await fetch('/api/interview', {  
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        githubId: githubId,  
        repoUrl: repoUrl,     
        vercelUrl: vercelUrl 
      })
    })

    const responseData = await response.json()

    if (!response.ok) {
      throw new Error(responseData.msg || `请求失败，状态码: ${response.status}`)
    }

    if (responseData.code === 0) {
      console.log('提交挑战成功', responseData)
      alert('提交挑战成功！' + responseData.msg)
      // 重置表单
      form.value = {
        githubId: '',
        repoUrl: '',
        vercelUrl: ''
      }
      handleClose()
    } else {
      alert('提交失败: ' + (responseData.msg || '未知错误'))
    }
  } catch (error) {
    console.error('提交过程出错', error)
    alert('提交失败，请稍后重试: ' + (error.message || '网络异常'))
  }
}
</script>

<style scoped>
/* 样式部分保持不变 */
.tabinfomation {
  display: flex;
  flex-direction: row;
  border-bottom: 1px dashed #ddd;
  padding: 12px 15px;
}

.acceptspan {
  font-size: 16px;
  flex: 1;
  font-weight: 600;
  text-align: center;
  color: #333;
}

.delect {
  height:20px;
  width:20%;
  font-size: small;
  font-weight: bold;
  border:none;
  background: none;
  color: black;
}
.delect:hover {
  background-color: #f5f5f5;
  color: #ff4d4f;
}

.interview-form {
  padding: 20px 15px;
}

.formgroup {
  margin-bottom: 20px;
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.formgroup label {
  font-size: 14px;
  color: #333;
  font-weight: 500;
}

.required {
  color: #ff4d4f;
  font-size: 14px;
}

.formgroup input {
  width: 100%;
  height: 42px;
  padding: 0 12px;
  border: 1px solid #ddd;
  border-radius: 6px;
  font-size: 15px;
  box-sizing: border-box;
  transition: all 0.2s;
}

.formgroup input:focus {
  outline: none;
  border-color: #00BFFF;
  box-shadow: 0 0 0 3px rgba(0, 191, 255, 0.1);
}

.formgroup button {
  width: 100%;
  height: 44px;
  font-size: 16px;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.formgroup button:hover {
  background-color: #359e6d;
}
</style>
    