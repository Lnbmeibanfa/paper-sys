import request from '@/utils/request.js'

const addPaperAPI = (data) => {
  return request.post('/paper/add', data)
}

const deleteByIdAPI = (id) => {
  return request.delete('/paper/deleteById/' + id)
}

const deleteBatchAPI = (ids) => {
  return request.delete('/paper/deleteBatch', { data: ids })
}

const updatePaperAPI = (data) => {
  return request.put('/paper/update', data)
}

const selectPaperDataAPI = (pageNum, pageSize, teacherId) => {
  return request.get('/paper/selectByPage', { params: { pageNum, pageSize, teacherId } })
}

const selectPaperByFilter = (filterCondition) => {
  return request.post('/paper/selectByFilter', filterCondition)
}

export {
  addPaperAPI,
  selectPaperDataAPI,
  deleteByIdAPI,
  deleteBatchAPI,
  updatePaperAPI,
  selectPaperByFilter,
}
