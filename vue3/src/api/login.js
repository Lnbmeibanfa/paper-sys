import request from '@/utils/request'

const loginAPI = (userInfo) => {
  return request.post('/login', userInfo)
}

export { loginAPI }
