<template>
  <div class="container">
    <h1>面试引导</h1>
    <div 
      v-if="htmlContent" 
      class="markdown-content markdown-body" 
      v-html="htmlContent"  
    ></div>
    <div class="btn-group">
    <button class="accept" @click="goToaccept">接受挑战</button>
    <button class="finish" @click="goTofinish">完成挑战</button>
    </div>
     <div 
      class="mask" 
      v-show="showuserinfomationform || showfinishchallenge"
    ></div>
    <transition name="fade">
    <div class="userinfomationform" v-show="showuserinfomationform">
      <div class="tabinfomation">
        <span class="acceptspan">接受挑战</span>
         <button class="delect" @click="close">X关闭</button>
      </div>
      <form @submit.prevent="handleSubmit" class="interview-form">
        <div class="formgroup">
          <label for="githubId">GitHub ID <span class="required">*</span></label>
          <input type="text" placeholder="请输入您的github id" v-model="githubId">
        </div>

        <div class="formgroup">
          <label for="email">Email <span class="required">*</span></label>
          <input type="text" placeholder="请输入您的email" v-model="email">
        </div>

        <div class="formgroup">
          <button>submit</button>
        </div>
      </form>
    </div>
    </transition>
    <FinishChallengeComponent 
      v-show="showfinishchallenge"
      @close="closeFinish"
    />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { marked } from 'marked'  // 引入 Markdown 转换库
import 'github-markdown-css'
import FinishChallengeComponent from './FinishChallenge-component.vue'

const htmlContent = ref('')
const showuserinfomationform=ref(false)
const githubId=ref('')
const email=ref('')
const showfinishchallenge=ref(false)
const markdownContent=`# HireWithCode - 面试者挑战项目

欢迎来到 \`HireWithCode\` 项目！这是一个专为技术面试者设计的真实项目挑战。目前，这个仓库刚刚起步，没有任何代码——只有待实现的功能（TODOs）。你的任务是将这些TODOs转变为实际的代码，提交你的Pull Request（PR）就是你面试的一部分挑战。

## 产品功能 TODOs
产品是一个 Web 网站（PC端、移动端兼容），分为 4 个步骤的页面：
- [ ] **欢迎语**：Logo + 一句话欢迎语（垂直水平居中，2 秒后自动进入下一页 *面试引导*）\`欢迎来到 无限状态 线上面试环节，期待你的加入！\` [logo](./logo.png) 
- [ ] **面试引导**：介绍面试的流程和说明（Markdown 文案展示，需要渲染为富文本样式）[页面文案 markdown](https://raw.githubusercontent.com/InfiniteStatesInc/HireWithCode/refs/heads/main/README.md)
- [ ] **接受挑战**：表单，\`input\`(用户输入自己的 github id) 、\`input\`(邮箱)、\`submit button\`(接受挑战)
- [ ] **完成挑战**：表单，\`input\`(github 仓库 url)、\`input\`([Vercel](https://vercel.app/) 在线体验地址)、\`submit button\`(提交作品)

## 如何参与？

1. 克隆仓库到你的本地机器。
2. 按照你对这个产品的理解，自行开发 TODO
4. 确保你的代码是可以运行的
5. 完成挑战后，通过Pull Request将你的代码提交给我们。
6. 在PR中附上你的Vercel在线体验链接。

> 备注说明：
> 这个项目的步骤 3、4 是是需要服务端能力的（API + 数据库），如果你不具备全栈的经验，可以考虑使用 [supabase](https://supabase.com/) 作为本项目的服务端（或者仅仅实现前端静态网页）

我们会审查每一个PR，并以此作为面试的一部分。我们期待看到你的创意和技术实力！

祝你好运！`
onMounted(() => {
  htmlContent.value = marked.parse(markdownContent)
});

const goToaccept=()=>{
  showuserinfomationform.value=true
}
const goTofinish = () => {
  showfinishchallenge.value = true
}
const closeFinish = () => {
  showfinishchallenge.value = false
}
const close=()=>{
  showuserinfomationform.value=false;
  githubId.value='';
  email.value='';
}
// 提交逻辑
const handleSubmit = async () => {
  // 表单验证部分保持不变
  if (!githubId.value) {
    alert('请输入GitHub ID')
    return
  }
  
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
  if (!email.value || !emailRegex.test(email.value)) {
    alert('请输入有效的邮箱地址')
    return
  }

  try {
    const requestData = {
      githubId: githubId.value,
      email: email.value
    }

    const response = await fetch('/api/interview', {  
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(requestData)
    })

    const responseData = await response.json()

    if (!response.ok) {
      throw new Error(responseData.msg || `请求失败，状态码: ${response.status}`)
    }

    if (responseData.code === 0) {
      console.log('接受挑战成功', responseData)
      alert('接受挑战成功！' + responseData.msg)
      close()
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
.container{
    width: 100vw;
    min-height: 100vh;
    display: flex;
    flex-direction: column; 
    justify-content: center; 
    align-items: center; 
    background-color: #f8f9fa;
    padding: 20px;
    box-sizing: border-box;
    position: relative;
}
.markdown-content{
    border-radius: 50px;
    background: white;
    width: 90%;
    height: auto;
}
.mask{
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.3); /* 半透明黑色，值越大越暗 */
  backdrop-filter: blur(2px); /* 可选：轻微模糊底层内容，增强朦胧感 */
  z-index: 5; /* 层级：遮罩 > 底层内容，且 < 表单 */
  transition: opacity 0.3s; 
}
.tabinfomation{
  display: flex;
  flex-direction: row;
  border-bottom: 1px dashed black;
}
button{
    width:100px;
    height: 40px;
    font-size: large;
    background-color: #00BFFF;
    color: white;
    border: 1px solid white;
    border-radius: 55px;
}
.delect{
  height:20px;
  width:20%;
  font-size: small;
  font-weight: bold;
  border:none;
  background: none;
  color: black;
  
}
.btn-group {
  display: flex;
  gap: 15px; 
  margin-top: 20px;
}
.finish {
  margin-top: 20px;
  background-color: #42b983;
}
.finish:hover {
  background-color: #359e6d;
}
.interview-form {
  padding: 20px 15px; /* 与顶部标题保持距离 */
}

/* 表单项容器 */
.formgroup {
  margin-bottom: 22px; /* 项与项之间的间距 */
  display: flex;
  flex-direction: column;
  gap: 8px; /* 标签与输入框的间距 */
}

/* 标签样式 */
.formgroup label {
  font-size: 14px;
  color: #333;
  font-weight: 500;
  padding-left: 2px; /* 与输入框左对齐 */
}

/* 必填项标记 */
.required {
  color: #ff4d4f; /* 红色标记 */
  font-size: 14px;
}
.form-input {
  width: 100%;
  height: 42px;
  padding: 0 12px; /* 左右内边距，方便输入 */
  border: 1px solid #ddd; /* 浅灰色边框 */
  border-radius: 6px; /* 轻微圆角 */
  font-size: 15px;
  box-sizing: border-box; /* 避免宽度溢出 */
  transition: all 0.2s; /* 交互动画 */
}
.acceptspan{
  font-size:larger;
  flex: 1;
  font-weight: bolder;
  text-align:center;
}
.accept{
   margin-top: 20px;
}
.accept:hover{
  background-color: #00a8e8; /*  hover时加深颜色 */
}
.userinfomationform{
   position: absolute;
   width: 300px;
   height: 250px;
   border: 1px solid white;
   background-color: #fff;
   border-radius: 12px;
   box-shadow: 0 10px 30px rgba(0, 0, 0, 0.15); 
   z-index: 10;
}
.delect:hover {
  background-color: #f5f5f5;
  color: #ff4d4f;
}
.fade-enter-from, .fade-leave-to {
  opacity: 0;
  transform: translateY(20px);
}
.fade-enter-active, .fade-leave-active {
  transition: opacity 0.3s, transform 0.3s;
}
.finishchallenge {
  position: absolute;
  width: 320px;
  background-color: #fff;
  border-radius: 12px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.15); 
  z-index: 10;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}


/* 适配移动端 */
@media (max-width: 768px) {
  .markdown-content {
    padding: 1rem;
    margin: 1rem 0;
  }
}
</style>