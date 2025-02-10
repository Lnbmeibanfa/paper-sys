import request from '@/utils/request.js'

const addCourseAPI = (data) => {
  return request.post('/course/add', data)
}

const deleteByIdAPI = (id) => {
  return request.delete('/course/deleteById/' + id)
}

const deleteBatchAPI = (ids) => {
  return request.delete('/course/deleteBatch', { data: ids })
}

const updateCourseAPI = (data) => {
  return request.put('/course/update', data)
}

const selectCourseDataAPI = (pageNum, pageSize, name) => {
  return request.get('/course/selectByPage', { params: { pageNum, pageSize, name } })
}

export { addCourseAPI, selectCourseDataAPI, deleteByIdAPI, deleteBatchAPI, updateCourseAPI }
