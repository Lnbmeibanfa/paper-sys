<script setup>
import { useRoute } from 'vue-router'
import { selectPaperById, recommend } from '@/api/paper'
import RecommendPaperShower from '@/views/component/RecommendPaperShower.vue'
import { ElMessage } from 'element-plus'
import { onMounted } from 'vue'
import { ref } from 'vue'
const route = useRoute()
const paper = ref({})
const recommendData = ref({})
const loadPaperData = () => {
  selectPaperById(route.query.id).then((res) => {
    if (res.code === '200') {
      console.log(res)
      paper.value = res.data
    } else {
      ElMessage.error(res.msg)
    }
  })
}
const loadRecommendData = () => {
  recommend().then((res) => {
    if (res.code === '200') {
      recommendData.value = res.data
    } else {
      ElMessage.error(res.msg)
    }
  })
}
onMounted(() => {
  loadPaperData()
  loadRecommendData()
})
</script>

<template>
  <div class="detail">
    <div class="detail-info">
      <div class="notice box">
        <div class="status">
          <el-tag v-if="paper.studentId === null" type="info">未有人选</el-tag>
          <el-tag v-else type="danger">已有人选</el-tag>
        </div>
        <div class="student-group">
          <el-tag type="warning">{{ paper.studentGroup }}</el-tag>
        </div>
      </div>
      <div class="name box">{{ paper.name }}</div>
      <div class="type box">{{ paper.type }}</div>
      <div class="resource box">{{ paper.resource }}</div>
      <div class="button-box">
        <el-button type="primary" plain icon="star" @click="collect">感兴趣</el-button>
        <el-button type="success" plain icon="chatRound" @click="communicate">立即沟通</el-button>
      </div>
    </div>
    <main>
      <div class="paper-detail card">
        <div class="title">职位要求</div>
        <div class="tags-box">
          <div class="course-box tag-item">
            <div class="tag-label">前置课程</div>
            <div class="tags">
              <el-tag class="tag" type="info" v-for="course in paper.courses" :key="course.id">{{
                course.name
              }}</el-tag>
            </div>
          </div>
          <div class="language-box tag-item">
            <div class="tag-label">编程语言</div>
            <div class="tags">
              <el-tag
                class="tag"
                type="info"
                v-for="language in paper.languages"
                :key="language.id"
                >{{ language.name }}</el-tag
              >
            </div>
          </div>
          <div class="technology-box tag-item">
            <div class="tag-label">相关技术</div>
            <div class="tags">
              <el-tag
                class="tag"
                type="info"
                v-for="technology in paper.technologies"
                :key="technology.id"
                >{{ technology.name }}</el-tag
              >
            </div>
          </div>
        </div>
        <div class="requirement">
          {{ paper.requirement }}
        </div>
      </div>
      <div class="aside">
        <div class="teacher-info-box">
          <div class="info-title">教师基本信息</div>
          <div class="info-box">
            <div class="img-box"><img :src="paper.teacherAvatar" alt="" /></div>
            <div class="direction">
              <el-icon><User /></el-icon>
              <div>{{ paper.teacherName }}</div>
            </div>
            <div class="direction">
              <el-icon><Reading /></el-icon>
              <div>{{ paper.teacherResearchDirection }}</div>
            </div>
            <div class="direction">
              <el-icon><Location /></el-icon>
              <div>{{ paper.teacherAddress }}</div>
            </div>
            <div class="button">
              <el-button type="success" plain @click="toMorePaper">查看全部论文</el-button>
            </div>
          </div>
        </div>
        <div class="recommend">
          <div class="info-title">推荐职位</div>
          <div class="recommends">
            <recommend-paper-shower v-for="paper in recommendData" :key="paper.id" :paper="paper" />
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<style scoped>
.box {
  margin-bottom: 10px;
}
.detail-info {
  color: white;
  background-color: #3bc575;
  padding: 20px 300px 30px 300px;
}
.notice {
  display: flex;
  font-size: 16px;
}
.status {
  margin-right: 12px;
}
.name {
  font-size: 38px;
  font-weight: 600;
}
main {
  display: flex;
  padding: 10px 300px;
}
.paper-detail {
  flex: 1;
  padding: 20px;
}
.title {
  font-size: 18px;
  font-weight: 600;
}
.tag-item {
  display: flex;
  align-items: center;
  margin: 10px 0px;
}
.tag-item .tag-label {
  margin-right: 10px;
}
.tag {
  margin-right: 5px;
}
.aside {
  margin-left: 10px;
  width: 300px;
}
.teacher-info-box {
  text-align: center;
  background-color: #fff;
  border-radius: 5px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  margin-bottom: 5px;
}
.info-title {
  font-size: 18px;
  font-weight: 600;
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
  background: linear-gradient(#f5fcfc, #fcfbfa);
  text-align: center;
  padding: 10px;
}
.teacher-info-box .info-box {
  padding: 20px;
}
.teacher-info-box .img-box img {
  border-radius: 10px;
  width: 80px;
  height: 80px;
}
.teacher-info-box .direction {
  margin: 10px 0px;
  display: flex;
  align-items: center;
  justify-content: center;
}
.recommend {
  text-align: center;
  background-color: #fff;
  border-radius: 5px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  margin-bottom: 5px;
}
.recommend .recommends {
  padding: 10px;
}
</style>
