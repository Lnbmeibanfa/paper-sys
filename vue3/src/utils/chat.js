import { useAccountStore } from '@/stores/account'

const accountStore = useAccountStore()
const chat = new WebSocket(`ws://localhost:9090/chat?token=${accountStore.accountInfo.token}`)

chat.onopen = () => {
  console.log('open')
}

chat.onmessage = () => {}

export default chat
