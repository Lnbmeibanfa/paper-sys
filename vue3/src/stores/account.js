import { defineStore } from 'pinia'
import { ref } from 'vue'
import { cloneDeep } from 'lodash'

export const useAccountStore = defineStore(
  'account',
  () => {
    const accountInfo = ref({})
    const setAccountInfo = (data) => {
      accountInfo.value = cloneDeep(data)
    }
    const setToken = (token) => {
      accountInfo.value.token = token
    }
    const getToken = () => {
      return accountInfo.value.token
    }
    return { accountInfo, setAccountInfo, setToken, getToken }
  },
  {
    persist: true,
  },
)
