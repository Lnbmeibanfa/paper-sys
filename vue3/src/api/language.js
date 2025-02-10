import request from '@/utils/request.js'

const addLanguageAPI = (data) => {
  return request.post('/language/add', data)
}

const deleteByIdAPI = (id) => {
  return request.delete('/language/deleteById/' + id)
}

const deleteBatchAPI = (ids) => {
  return request.delete('/language/deleteBatch', { data: ids })
}

const updateLanguageAPI = (data) => {
  return request.put('/language/update', data)
}

const selectLanguageDataAPI = (pageNum, pageSize, name) => {
  return request.get('/language/selectByPage', { params: { pageNum, pageSize, name } })
}

export { addLanguageAPI, selectLanguageDataAPI, deleteByIdAPI, deleteBatchAPI, updateLanguageAPI }
