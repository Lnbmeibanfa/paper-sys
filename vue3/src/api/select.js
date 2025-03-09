import request from '@/utils/request.js'

const addSelectAPI = (data) => {
  return request.post('/select/add', data)
}

const deleteBySelectAPI = (stduentId, paperId) => {
  return request.delete(`/select/deleteBySelect?studentId=${stduentId}&paperId=${paperId}`)
}

const updateSelectAPI = (data) => {
  return request.put('/select/update', data)
}

const selectBySelectAPI = (stduentId, paperId) => {
  return request.get('/select/selectBySelect', { params: { stduentId, paperId } })
}

export { addSelectAPI, selectBySelectAPI, deleteBySelectAPI, updateSelectAPI }
