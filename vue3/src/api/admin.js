import request from '@/utils/request.js'

const addAdminAPI = (data) => {
  return request.post('/admin/add', data)
}

const deleteByIdAPI = (id) => {
  return request.delete('/admin/deleteById/' + id)
}

const deleteBatchAPI = (ids) => {
  return request.delete('/admin/deleteBatch', { data: ids })
}

const updateAdminAPI = (data) => {
  return request.put('/admin/update', data)
}

const selectAdminDataAPI = (pageNum, pageSize, name) => {
  return request.get('/admin/selectByPage', { params: { pageNum, pageSize, name } })
}

export { addAdminAPI, selectAdminDataAPI, deleteByIdAPI, deleteBatchAPI, updateAdminAPI }
