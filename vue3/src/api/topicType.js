import request from '@/utils/request.js'

const addTopicTypeAPI = (data) => {
  return request.post('/topicType/add', data)
}

const deleteByIdAPI = (id) => {
  return request.delete('/topicType/deleteById/' + id)
}

const deleteBatchAPI = (ids) => {
  return request.delete('/topicType/deleteBatch', { data: ids })
}

const updateTopicTypeAPI = (data) => {
  return request.put('/topicType/update', data)
}

const selectTopicTypeDataAPI = (pageNum, pageSize, name) => {
  return request.get('/topicType/selectByPage', { params: { pageNum, pageSize, name } })
}

export {
  addTopicTypeAPI,
  selectTopicTypeDataAPI,
  deleteByIdAPI,
  deleteBatchAPI,
  updateTopicTypeAPI,
}
