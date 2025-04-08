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
// 查询所有论文数量
const getAllPaper = () => {
  return request.get('/admin/publishedPaper')
}
// 查询选择论文数量
const getSelectedPaper = () => {
  return request.get('/admin/selectedPaper')
}
// 查询所有学生数量
const getAllStudent = () => {
  return request.get('/admin/studentCount')
}
export {
  addAdminAPI,
  selectAdminDataAPI,
  deleteByIdAPI,
  deleteBatchAPI,
  updateAdminAPI,
  getAllPaper,
  getSelectedPaper,
  getAllStudent,
}
