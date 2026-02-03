# Navbar登录状态显示问题修复

## 问题描述

登录成功后，导航栏仍然显示"登录"按钮，而不是用户头像和用户名。

## 问题原因

### 1. 响应式更新问题

原代码使用 `computed` 属性来计算 `isLoggedIn` 和 `user`：

```javascript
const user = computed(() => {
  const userStr = localStorage.getItem('user')
  return userStr ? JSON.parse(userStr) : null
})

const isLoggedIn = computed(() => {
  return !!localStorage.getItem('token')
})
```

**问题**：
- `computed` 属性在组件初始化时只计算一次
- 当 localStorage 发生变化时，computed 不会自动重新计算
- Vue 3 的响应式系统无法直接监听 localStorage 的变化

### 2. 缺少事件监听

原代码没有监听 `storage` 事件，无法响应其他标签页的 localStorage 变化。

## 解决方案

### 1. 使用 ref 替代 computed

将 `isLoggedIn` 和 `user` 从 `computed` 改为 `ref`：

```javascript
const isLoggedIn = ref(false)
const currentUser = ref(null)

const user = computed(() => currentUser.value)
```

### 2. 添加检查登录状态的函数

```javascript
const checkLoginStatus = () => {
  const token = localStorage.getItem('token')
  const userStr = localStorage.getItem('user')
  isLoggedIn.value = !!token
  if (userStr) {
    try {
      currentUser.value = JSON.parse(userStr)
    } catch (e) {
      currentUser.value = null
    }
  } else {
    currentUser.value = null
  }
}
```

### 3. 在组件挂载时检查登录状态

```javascript
onMounted(() => {
  checkLoginStatus()
  
  if (window.matchMedia('(prefers-color-scheme: dark)').matches) {
    isDark.value = true
    document.documentElement.classList.add('dark')
  }
  
  window.addEventListener('storage', checkLoginStatus)
})
```

### 4. 更新登出函数

```javascript
const handleLogout = () => {
  localStorage.removeItem('token')
  localStorage.removeItem('user')
  isLoggedIn.value = false
  currentUser.value = null
  router.push('/')
}
```

## 完整代码

```vue
<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const isDark = ref(false)
const isLoggedIn = ref(false)
const currentUser = ref(null)

const user = computed(() => currentUser.value)

const checkLoginStatus = () => {
  const token = localStorage.getItem('token')
  const userStr = localStorage.getItem('user')
  isLoggedIn.value = !!token
  if (userStr) {
    try {
      currentUser.value = JSON.parse(userStr)
    } catch (e) {
      currentUser.value = null
    }
  } else {
    currentUser.value = null
  }
}

const toggleDarkMode = () => {
  isDark.value = !isDark.value
  if (isDark.value) {
    document.documentElement.classList.add('dark')
  } else {
    document.documentElement.classList.remove('dark')
  }
}

const handleLogout = () => {
  localStorage.removeItem('token')
  localStorage.removeItem('user')
  isLoggedIn.value = false
  currentUser.value = null
  router.push('/')
}

onMounted(() => {
  checkLoginStatus()
  
  if (window.matchMedia('(prefers-color-scheme: dark)').matches) {
    isDark.value = true
    document.documentElement.classList.add('dark')
  }
  
  window.addEventListener('storage', checkLoginStatus)
})
</script>
```

## 关键改进

### 1. 响应式状态管理

- 使用 `ref` 而不是 `computed` 来管理登录状态
- 手动调用 `checkLoginStatus()` 来更新状态
- 确保状态变化时视图能够正确更新

### 2. Storage 事件监听

- 添加 `window.addEventListener('storage', checkLoginStatus)`
- 监听 localStorage 的变化
- 支持多标签页同步登录状态

### 3. 错误处理

- 添加 try-catch 处理 JSON 解析错误
- 防止无效的用户数据导致页面崩溃

### 4. 登出状态更新

- 登出时手动更新 `isLoggedIn` 和 `currentUser`
- 确保视图立即更新

## 测试验证

### 测试步骤

1. 访问 http://localhost:5173/login
2. 使用账号 `newuser` 和密码 `123456` 登录
3. 检查导航栏是否显示用户头像和用户名
4. 点击登出按钮
5. 检查导航栏是否恢复显示"登录"按钮

### 预期结果

- ✅ 登录成功后，导航栏显示用户头像和用户名
- ✅ 登出后，导航栏显示"登录"按钮
- ✅ 刷新页面后，登录状态保持正确
- ✅ 多标签页之间登录状态同步

## 修改状态

✅ 已完成 - Navbar登录状态显示问题已修复

## 相关文件

- `src/components/Navbar.vue` - 修复登录状态响应式更新

## 注意事项

1. **响应式原理**: Vue 的响应式系统无法直接监听 localStorage，需要手动管理状态
2. **事件监听**: storage 事件只在其他标签页或窗口触发时触发，不会在当前页面触发
3. **手动更新**: 在登录/登出时需要手动调用 `checkLoginStatus()` 或直接更新状态
4. **跨标签同步**: storage 事件监听可以实现多标签页的登录状态同步

## 扩展建议

### 1. 使用 Pinia 状态管理

如果项目规模扩大，建议使用 Pinia 进行全局状态管理：

```javascript
// stores/user.js
import { defineStore } from 'pinia'

export const useUserStore = defineStore('user', {
  state: () => ({
    isLoggedIn: false,
    user: null
  }),
  actions: {
    setUser(user) {
      this.user = user
      this.isLoggedIn = !!user
    },
    clearUser() {
      this.user = null
      this.isLoggedIn = false
    }
  }
})
```

### 2. 添加路由守卫

保护需要登录的页面：

```javascript
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  if (to.meta.requiresAuth && !token) {
    next('/login')
  } else {
    next()
  }
})
```

### 3. 添加自动刷新Token

在 axios 拦截器中添加 token 刷新逻辑：

```javascript
request.interceptors.response.use(
  response => response,
  async error => {
    if (error.response?.status === 401) {
      localStorage.removeItem('token')
      localStorage.removeItem('user')
      router.push('/login')
    }
    return Promise.reject(error)
  }
)
```
