<script setup>
import { watch } from 'vue'
import { ref } from 'vue'

const props = defineProps({
  title: String,
  name: String,
  content: String,
  placeholder: String,
})
const emit = defineEmits(['save'])
watch(
  () => props.content,
  (newVal) => {
    editContent.value = newVal
  },
)
const onEdit = ref(false)
const editVisiable = ref(false)
const editContent = ref(props.content)
const save = () => {
  emit('save', editContent.value)
  onEdit.value = false
}
</script>

<template>
  <div class="edit" v-if="onEdit">
    <el-input
      class="edit-input"
      v-model="editContent"
      style="width: 240px"
      :placeholder="placeholder"
      clearable
    />
    <el-button type="primary" @click="save">保存</el-button>
    <el-button type="warning" @click="onEdit = false">取消</el-button>
  </div>
  <div class="shower" @mouseenter="editVisiable = true" @mouseleave="editVisiable = false" v-else>
    <div class="title">{{ title }}</div>
    <div class="show">{{ content }}</div>
    <div class="edit-link" v-show="editVisiable" @click="onEdit = true">
      <el-link :underline="false"
        ><el-icon><EditPen /></el-icon>修改
      </el-link>
    </div>
  </div>
</template>

<style scoped>
.shower {
  display: flex;
}
.title {
  width: 100px;
  font-size: 15px;
  font-weight: 600;
}
.show {
  font-size: 15px;
  margin-right: 10px;
}
.edit-link {
  font-size: 13px;
}
.edit-input {
  margin-right: 10px;
}
</style>
