import request from '@/utils/request.js'

/**
 * 
 * @param {
    private Integer studentId;
    private Integer paperId;
 * } data 
 * @returns 
 */
const addSelectAPI = (data) => {
  return request.post('/select/add', data)
}

const deleteBySelectAPI = (studentId, paperId) => {
  return request.delete(`/select/deleteBySelect?studentId=${studentId}&paperId=${paperId}`)
}

const updateSelectAPI = (data) => {
  return request.put('/select/update', data)
}

const selectBySelectAPI = (studentId, paperId) => {
  return request.get('/select/selectBySelect', { params: { studentId, paperId } })
}

export { addSelectAPI, selectBySelectAPI, deleteBySelectAPI, updateSelectAPI }
