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

const selectPaperDataAPI = (pageNum, pageSize, name) => {
  return request.get('/paper/selectByPage', { params: { pageNum, pageSize, name } })
}

export { addPaperAPI, selectPaperDataAPI, deleteByIdAPI, deleteBatchAPI, updatePaperAPI }
