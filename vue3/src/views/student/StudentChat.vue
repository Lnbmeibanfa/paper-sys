<script setup>
import SessionContacts from '../component/SessionContacts.vue'
import SessionMessage from '../component/SessionMessage.vue'
import { selectMessageDataAPI } from '@/api/message'
import { selectRecentContactDataAPI } from '@/api/recentContact'
import { addSelectAPI } from '@/api/select'
import chat from '@/utils/chat'
import '@wangeditor/editor/dist/css/style.css'
import { onBeforeUnmount, ref, shallowRef } from 'vue'
import { Editor, Toolbar } from '@wangeditor/editor-for-vue'
import { useAccountStore } from '@/stores/account'
import { onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { reactive } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
const pageNum = ref(1)
const pageSize = ref(10)
const route = useRoute()
const accountInfo = useAccountStore()
// 当前对话的teacherId和paperId
// 最近会话
const sessionData = ref(null)
const curSession = ref({
  paperId: null,
  contactId: null,
})
// 单个会话数据
const messageData = ref(null)
// 发送新信息模板
const messageBuilder = reactive({
  paperId: '',
  content: '',
  receiverId: '',
  receiverRole: 'TEACHER',
  sendRole: 'STUDENT',
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
  console.log(curSession)
  selectMessageDataAPI(
    pageNum.value,
    pageSize.value,
    accountInfo.accountInfo.id,
    curSession.value.contactId,
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
  curSession.value.paperId = route.query.paperId || null
  curSession.value.contactId = route.query.teacherId || null
  loadMessageData()
  loadRecentSession()
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
}
chat.onmessage = () => {
  loadMessageData()
}
const selectPaper = () => {
  ElMessageBox.confirm('你只能选择一个论文，确认选择？', '确认选择', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning',
  })
    .then(() => {
      addSelectAPI({
        studentId: accountInfo.accountInfo.id,
        paperId: curSession.value.paperId,
      }).then((res) => {
        if (res.code === '200') {
          ElMessage.success('已经成功选择！')
        } else {
          ElMessage.error(res.msg)
        }
      })
    })
    .catch((err) => console.log(err))
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
        <div class="teacher-name"></div>
        <div class="teacher-research-direction"></div>
      </div>
      <div class="paper-info">
        <div>{{ curSession.paperName }}</div>
        <div>{{ curSession.teacherName }}</div>
      </div>
      <el-scrollbar ref="scrollBarRef" class="message-box"
        ><session-message v-for="message in messageData" :key="message.id" :message="message"
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
          <el-button type="primary" @click="selectPaper" v-show="curSession.selectable"
            >选择该论文</el-button
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
  display: flex;
}
.send-btn {
  float: right;
}
.paper-info {
  border-bottom: 1px solid #e4e4e4;
}
/**element */
.el-empty {
  width: 100%;
  background-color: #fff;
}
</style>
