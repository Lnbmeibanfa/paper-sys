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

/**
 * @param {*} filterCondition            {
    keyword: '',
    courseIds: [],
    languageIds: [],
    technologyIds: [],
  }
 * @returns List<paper>
 */
const selectPaperByFilter = (filterCondition) => {
  return request.post('/paper/selectByFilter', filterCondition)
}

const selectPaperById = (id) => {
  return request.get('/paper/selectById', { params: { id } })
}
/**
 * 查询学生选择的论文
 * @param  studentId 学生id
 * @returns paper
 */
const selectSelectedPaper = (studentId) => {
  return request.get('/paper/selectSelectedPaper', { params: { studentId } })
}

const recommend = () => {
  return request.get('/paper/recommend')
}

export {
  addPaperAPI,
  selectPaperDataAPI,
  deleteByIdAPI,
  deleteBatchAPI,
  updatePaperAPI,
  selectPaperByFilter,
  selectPaperById,
  selectSelectedPaper,
  recommend,
}
