import './assets/css/global.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import { createPersistedState } from 'pinia-plugin-persistedstate'
import App from './App.vue'
import router from './router'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

const app = createApp(App)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}
app.use(createPinia().use(createPersistedState()))
app.use(ElementPlus)
app.use(router)

app.mount('#app')
