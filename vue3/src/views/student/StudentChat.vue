<script setup>
import SessionContacts from '../component/SessionContacts.vue'
import SessionMessage from '../component/SessionMessage.vue'
import '@wangeditor/editor/dist/css/style.css'
import { onBeforeUnmount, ref, shallowRef, onMounted } from 'vue'
import { Editor, Toolbar } from '@wangeditor/editor-for-vue'
import { useAccountStore } from '@/stores/account'
const accountInfo = useAccountStore()
// 编辑器实例，必须用 shallowRef
const editorRef = shallowRef()
// 内容 HTML
const valueHtml = ref('<p>hello</p>')

// 模拟 ajax 异步获取内容
onMounted(() => {
  setTimeout(() => {
    valueHtml.value = '<p>模拟 Ajax 异步设置内容</p>'
  }, 1500)
})
const toolbarConfig = {
  toolbarKeys: ['uploadImage', 'emotion'],
  MENU_CONF: {},
}
const editorConfig = {
  MENU_CONF: {},
}
// 图片上传配置
editorConfig.MENU_CONF['uploadImage'] = {
  headers: {
    token: accountInfo.accountInfo.token,
  },
  server: 'http://localhost:9090/files/wang/upload', // 服务端图片上传接口
  fieldName: 'file', // 服务端图片上传接口参数
}
// 组件销毁时，也及时销毁编辑器
onBeforeUnmount(() => {
  const editor = editorRef.value
  if (editor == null) return
  editor.destroy()
})
const handleCreated = (editor) => {
  editorRef.value = editor // 记录 editor 实例，重要！
}
// 测试数据
const data = ref({
  name: 'sir',
  researchDirection: '天文',
})
const testMessage = ref({
  content: '<span data-slate-string="true">模拟 Ajax 异步设置内容testMessage</span>',
  sendRole: 'STUDENT',
  createTime: '19:21',
})
</script>

<template>
  <div class="student-chat">
    <div class="contacts">
      <session-contacts :teacher="data" />
    </div>
    <div class="chat-box">
      <div class="contact-info">
        <div class="teacher-name">{{ data.name }}</div>
        <div class="teacher-research-direction">{{ data.researchDirection }}</div>
      </div>
      <div class="paper-info">论文名字</div>
      <div class="message-box">
        <session-message :message="testMessage" />
      </div>
      <div class="input-box">
        <div style="border: 1px solid #ccc">
          <Toolbar
            style="border-bottom: 1px solid #ccc"
            :editor="editorRef"
            :defaultConfig="toolbarConfig"
            mode="default"
          />
          <Editor
            style="height: 500px; overflow-y: hidden"
            v-model="valueHtml"
            :defaultConfig="editorConfig"
            mode="default"
            @onCreated="handleCreated"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* 这里编写样式代码 */
.student-chat {
  display: flex;
  padding: 10px;
  border-radius: 10px;
  width: 1200px;
  margin: 10px auto;
}
.contacts {
  width: 300px;
  border-radius: 10px;
  padding: 20px;
  background-color: #fff;
  margin-right: 10px;
}
.chat-box {
  border-radius: 10px;
  padding: 20px;
  background-color: #fff;
  flex: 1;
}
.contact-info {
  display: flex;
}
</style>
