import request from '@/utils/request.js'

const selectRecentContactDataAPI = (pageNum, pageSize, userId, userRole) => {
  return request.get('/recentContact/selectByPage', {
    params: { pageNum, pageSize, userId, userRole },
  })
}

const authorSelectAPI = (recentContact) => {
  return request.put('/recentContact/author', recentContact)
}

export { selectRecentContactDataAPI, authorSelectAPI }
