import { defineStore } from 'pinia'
import { ref } from 'vue'
import { cloneDeep } from 'lodash'

export const useAccountStore = defineStore(
  'account',
  () => {
    const accountInfo = ref({})
    const addAccountInfo = (data) => {
      for (const each in data) {
        if (data[each] !== null && data[each] !== undefined) {
          accountInfo.value[each] = data[each]
        }
      }
    }
    const cloneAccountInfo = (data) => {
      accountInfo.value = cloneDeep(data)
    }
    const setToken = (token) => {
      accountInfo.value.token = token
    }
    const getToken = () => {
      return accountInfo.value.token
    }
    return { accountInfo, cloneAccountInfo, addAccountInfo, setToken, getToken }
  },
  {
    persist: true,
  },
)
