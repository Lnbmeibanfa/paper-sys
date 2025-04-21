import request from '@/utils/request.js'

const selectRecentContactDataAPI = (pageNum, pageSize, userId, userRole) => {
  return request.get('/recentContact/selectByPage', {
    params: { pageNum, pageSize, userId, userRole },
  })
}

/**
 * 
 * @param {
    private Integer userId;
    private Role userRole;
    private Integer contactId;
    private Role contactRole;
    private Integer paperId;
  } rc 
 */
const addRecentContactAPI = (rc) => {
  return request.put('/recentContact/add', rc)
}

/**
 * 授权/取消授权学生选择论文
 * @param recentContact {
    selectable: true,
    userId: accountInfo.accountInfo.id,
    userRole: accountInfo.accountInfo.role,
  } 
 * @returns 
 */
const authorSelectAPI = (recentContact) => {
  return request.put('/recentContact/author', recentContact)
}

export { selectRecentContactDataAPI, authorSelectAPI, addRecentContactAPI }
