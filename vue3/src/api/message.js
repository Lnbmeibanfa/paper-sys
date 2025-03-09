import request from '@/utils/request.js'

const selectMessageDataAPI = (pageNum, pageSize, sendId, receiverId, paperId) => {
  return request.get('/message/selectByPage', {
    params: { pageNum, pageSize, sendId, receiverId, paperId },
  })
}

export { selectMessageDataAPI }
