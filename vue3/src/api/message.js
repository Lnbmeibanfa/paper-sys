import request from '@/utils/request.js'

const selectMessageDataAPI = (
  pageNum,
  pageSize,
  sendId,
  sendRole,
  receiverId,
  receiverRole,
  paperId,
) => {
  return request.get('/message/selectByPage', {
    params: { pageNum, pageSize, sendId, sendRole, receiverId, receiverRole, paperId },
  })
}

export { selectMessageDataAPI }
