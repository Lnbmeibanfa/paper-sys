import request from '@/utils/request.js'

const addTeacherAPI = (data) => {
  return request.post('/teacher/add', data)
}

const deleteByIdAPI = (id) => {
  return request.delete('/teacher/deleteById/' + id)
}

const deleteBatchAPI = (ids) => {
  return request.delete('/teacher/deleteBatch', { data: ids })
}

const updateTeacherAPI = (data) => {
  return request.put('/teacher/update', data)
}

const selectTeacherDataAPI = (pageNum, pageSize, name) => {
  return request.get('/teacher/selectByPage', { params: { pageNum, pageSize, name } })
}

export { addTeacherAPI, selectTeacherDataAPI, deleteByIdAPI, deleteBatchAPI, updateTeacherAPI }
