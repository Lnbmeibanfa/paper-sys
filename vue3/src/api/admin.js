import request from '@/utils/request.js'

const addAdminAPI = (data) => {
  return request.post('/admin/add', data)
}

const selectAdminDataAPI = () => {
  return request.get('/admin/selectByPage')
}

export { addAdminAPI, selectAdminDataAPI }
