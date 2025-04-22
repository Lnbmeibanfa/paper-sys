<script setup>
import SessionContacts from '../component/SessionContacts.vue'
import SessionMessage from '../component/SessionMessage.vue'
import { selectMessageDataAPI } from '@/api/message'
import { selectRecentContactDataAPI } from '@/api/recentContact'
import { confirmPaper } from '@/api/paper'
import chat from '@/utils/chat'
import '@wangeditor/editor/dist/css/style.css'
import { onBeforeUnmount, ref, shallowRef } from 'vue'
import { Editor, Toolbar } from '@wangeditor/editor-for-vue'
import { useAccountStore } from '@/stores/account'
import { onMounted } from 'vue'
import { reactive } from 'vue'
import { ElMessage } from 'element-plus'
import { nextTick } from 'vue'
const pageNum = ref(1)
const pageSize = ref(10)
const accountInfo = useAccountStore()
// 当前对话
const curSession = ref({
  paperId: null,
  contactId: null,
})
// 最近会话数据
const sessionData = ref(null)
// 单个会话数据
const messageData = ref(null)
// 发送新信息模板
const messageBuilder = reactive({
  paperId: '',
  content: '',
  receiverId: '',
  receiverRole: 'STUDENT',
  sendRole: 'TEACHER',
  sendId: '',
})
// 加载左侧会话栏信息
const loadRecentSession = () => {
  selectRecentContactDataAPI(
    pageNum.value,
    pageSize.value,
    accountInfo.accountInfo.id,
    accountInfo.accountInfo.role,
  ).then((res) => {
    if (res.code === '200') {
      sessionData.value = res.data.list
    } else {
      ElMessage.error(res.msg)
    }
  })
}
// 加载对应teacher ，paper的会话信息
const loadMessageData = () => {
  selectMessageDataAPI(
    pageNum.value,
    pageSize.value,
    accountInfo.accountInfo.id,
    accountInfo.accountInfo.role,
    curSession.value.contactId,
    curSession.value.contactRole,
    curSession.value.paperId,
  ).then((res) => {
    if (res.code === '200') {
      messageData.value = res.data.list
      messageData.value.reverse()
    } else {
      ElMessage.error(res.msg)
    }
  })
}
// 滚动条实例
const scrollBarRef = ref(null)
const scrollToBottom = () => {}
/**wangEditor配置 */
// 编辑器实例，必须用 shallowRef
const editorRef = shallowRef()
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
const handleCreated = (editor) => {
  editorRef.value = editor // 记录 editor 实例，重要！
}
const onEnterSession = (session) => {
  curSession.value = session
  loadMessageData()
}
// 组件销毁时，也及时销毁编辑器
onBeforeUnmount(() => {
  const editor = editorRef.value
  if (editor == null) return
  editor.destroy()
})
onMounted(() => {
  loadMessageData()
  loadRecentSession()
  scrollToBottom()
})
/** webSocket相关配置 */
const sendMsg = () => {
  if (messageBuilder.content === '<p><br></p>') {
    ElMessage.error('不能发送空白消息')
    return
  }
  messageBuilder.paperId = curSession.value.paperId
  messageBuilder.receiverId = curSession.value.contactId
  messageBuilder.sendId = accountInfo.accountInfo.id
  chat.send(JSON.stringify(messageBuilder))
  messageBuilder.content = ''
  loadMessageData()
  nextTick(() => {
    scrollToBottom()
  })
}
chat.onmessage = () => {
  loadMessageData()
}
/**允许学生选择该论文 */
const authorSelect = () => {
  const paper = {
    studentId: curSession.value.contactId,
    id: curSession.value.paperId,
    isSelect: true,
  }
  confirmPaper(paper).then((res) => {
    if (res.code === '200') {
      ElMessage.success('已允许该学生选择论文')
      curSession.value.isSelect = true
    } else {
      ElMessage.error(res.msg)
    }
  })
}
const recusalSelect = () => {
  const paper = {
    studentId: curSession.value.contactId,
    paperId: curSession.value.paperId,
    isSelect: false,
  }
  confirmPaper(paper).then((res) => {
    if (res.code === '200') {
      ElMessage.success('已取消该学生选择论文')
      curSession.value.isSelect = false
    } else {
      ElMessage.error(res.msg)
    }
  })
}
</script>

<template>
  <div class="student-chat">
    <div class="contacts">
      <session-contacts
        @enterSession="onEnterSession"
        v-for="session in sessionData"
        :key="session.userId"
        :session="session"
      />
    </div>
    <div v-if="curSession.paperId" class="chat-box">
      <div class="contact-info">
        <div class="student-name item">{{ curSession.studentName }}</div>
        <div class="student-research-direction item">{{ curSession.studentResearchDirection }}</div>
      </div>
      <div class="paper-info" :class="{ 'bd-btm': curSession['paperName'] !== undefined }">
        <div>{{ curSession.paperName }}</div>
      </div>
      <el-scrollbar ref="scrollBarRef" class="message-box"
        ><session-message v-for="message in messageData" :key="message.content" :message="message"
      /></el-scrollbar>
      <div class="input-box">
        <div style="border-top: 1px solid #ccc">
          <Toolbar
            style="border-bottom: 1px solid #ccc"
            :editor="editorRef"
            :defaultConfig="toolbarConfig"
            mode="default"
          />
          <Editor
            style="height: 200px; overflow-y: hidden"
            v-model="messageBuilder.content"
            :defaultConfig="editorConfig"
            mode="default"
            @onCreated="handleCreated"
          />
        </div>
        <div class="button-container">
          <el-button
            type="warning"
            v-show="curSession.paperId !== null"
            @click="recusalSelect"
            v-if="curSession.isSelect"
            >取消选择该同学</el-button
          >
          <el-button
            type="primary"
            v-show="curSession.paperId !== null"
            @click="authorSelect"
            v-else
            >选择该同学</el-button
          >
          <el-button class="send-btn" type="success" @keydown.enter="sendMsg" @click="sendMsg"
            >发送</el-button
          >
        </div>
      </div>
    </div>
    <div v-else class="chat-box"><el-empty description="请选择联系人" /></div>
  </div>
</template>

<style scoped>
.student-chat {
  height: 800px;
}
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
  display: flex;
  flex-direction: column;
  border-radius: 10px;
  padding: 20px;
  background-color: #fff;
  flex: 1;
}
.message-box {
  padding: 0px 20px;
  height: 450px;
}
.contact-info {
  color: #666;
  display: flex;
  margin-bottom: 10px;
}
.paper-info {
  padding: 10px 0px;
  font-size: 16px;
}
.contact-info .item {
  margin-right: 10px;
}
.send-btn {
  float: right;
}
.bd-btm {
  border-bottom: 1px solid #e4e4e4;
}
</style>
