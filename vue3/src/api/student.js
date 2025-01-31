import request from '@/utils/request.js'

const addStudentAPI = (data) => {
  return request.post('/student/add', data)
}

const deleteByIdAPI = (id) => {
  return request.delete('/student/deleteById/' + id)
}

const deleteBatchAPI = (ids) => {
  return request.delete('/student/deleteBatch', { data: ids })
}

const updateStudentAPI = (data) => {
  return request.put('/student/update', data)
}

const selectStudentDataAPI = (pageNum, pageSize, name) => {
  return request.get('/student/selectByPage', { params: { pageNum, pageSize, name } })
}

export { addStudentAPI, selectStudentDataAPI, deleteByIdAPI, deleteBatchAPI, updateStudentAPI }
