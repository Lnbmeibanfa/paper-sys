import request from '@/utils/request.js'

const addAdminAPI = (data) => {
  console.log(data)
  return request.post('/admin/add', data)
}

const deleteByIdAPI = (id) => {
  return request.delete('/admin/deleteById/' + id)
}

const deleteBatchAPI = (ids) => {
  return request.delete('/admin/deleteBatch', { data: ids })
}

const selectAdminDataAPI = () => {
  return request.get('/admin/selectByPage')
}

export { addAdminAPI, selectAdminDataAPI, deleteByIdAPI, deleteBatchAPI }
