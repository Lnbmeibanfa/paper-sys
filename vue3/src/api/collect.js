import request from '@/utils/request.js'

const addCollectAPI = (data) => {
  return request.post('/collect/add', data)
}

const deleteByCollectAPI = (stduentId, paperId) => {
  return request.delete(`/collect/deleteByCollect?studentId=${stduentId}&paperId=${paperId}`)
}

const updateCollectAPI = (data) => {
  return request.put('/collect/update', data)
}

const selectByCollectAPI = (stduentId, paperId) => {
  return request.get('/collect/selectByCollect', { params: { stduentId, paperId } })
}

export { addCollectAPI, selectByCollectAPI, deleteByCollectAPI, updateCollectAPI }
