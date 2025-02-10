import request from '@/utils/request.js'

const addTechnologyAPI = (data) => {
  return request.post('/technology/add', data)
}

const deleteByIdAPI = (id) => {
  return request.delete('/technology/deleteById/' + id)
}

const deleteBatchAPI = (ids) => {
  return request.delete('/technology/deleteBatch', { data: ids })
}

const updateTechnologyAPI = (data) => {
  return request.put('/technology/update', data)
}

const selectTechnologyDataAPI = (pageNum, pageSize, name) => {
  return request.get('/technology/selectByPage', { params: { pageNum, pageSize, name } })
}

export {
  addTechnologyAPI,
  selectTechnologyDataAPI,
  deleteByIdAPI,
  deleteBatchAPI,
  updateTechnologyAPI,
}
