<template>
  <div class="min-h-screen bg-gradient-to-br from-primary-50 to-primary-100 dark:from-gray-900 dark:to-gray-800 flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
    <div class="max-w-md w-full space-y-8">
      <div class="text-center">
        <h2 class="text-3xl font-bold text-gray-900 dark:text-white">
          创建账号
        </h2>
        <p class="mt-2 text-sm text-gray-600 dark:text-gray-400">
          加入校园二手书交易平台
        </p>
      </div>

      <div class="card p-8">
        <form class="space-y-6" @submit.prevent="handleRegister">
          <div>
            <label for="username" class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
              用户名
            </label>
            <input
              id="username"
              v-model="formData.username"
              type="text"
              required
              class="input w-full"
              placeholder="请输入用户名"
              :disabled="loading"
            >
          </div>

          <div>
            <label for="phone" class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
              手机号
            </label>
            <input
              id="phone"
              v-model="formData.phone"
              type="tel"
              required
              class="input w-full"
              placeholder="请输入手机号"
              :disabled="loading"
            >
          </div>

          <div>
            <label for="email" class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
              邮箱
            </label>
            <input
              id="email"
              v-model="formData.email"
              type="email"
              required
              class="input w-full"
              placeholder="请输入邮箱"
              :disabled="loading"
            >
          </div>

          <div>
            <label for="password" class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
              密码
            </label>
            <input
              id="password"
              v-model="formData.password"
              type="password"
              required
              class="input w-full"
              placeholder="请输入密码（至少6位）"
              :disabled="loading"
            >
          </div>

          <div>
            <label for="confirmPassword" class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
              确认密码
            </label>
            <input
              id="confirmPassword"
              v-model="formData.confirmPassword"
              type="password"
              required
              class="input w-full"
              placeholder="请再次输入密码"
              :disabled="loading"
            >
          </div>

          <div v-if="errorMessage" class="bg-red-50 dark:bg-red-900/20 border border-red-200 dark:border-red-800 text-red-600 dark:text-red-400 px-4 py-3 rounded-lg">
            {{ errorMessage }}
          </div>

          <div v-if="successMessage" class="bg-green-50 dark:bg-green-900/20 border border-green-200 dark:border-green-800 text-green-600 dark:text-green-400 px-4 py-3 rounded-lg">
            {{ successMessage }}
          </div>

          <div>
            <button
              type="submit"
              class="btn btn-primary w-full text-lg py-3"
              :disabled="loading"
            >
              <span v-if="loading" class="flex items-center justify-center">
                <svg class="animate-spin -ml-1 mr-3 h-5 w-5 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                  <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                  <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                </svg>
                注册中...
              </span>
              <span v-else>注册</span>
            </button>
          </div>

          <div class="text-center text-sm text-gray-600 dark:text-gray-400">
            已有账号？
            <router-link to="/login" class="text-primary-600 dark:text-primary-400 hover:underline">
              立即登录
            </router-link>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { register } from '@/api/index'

const router = useRouter()

const formData = ref({
  username: '',
  phone: '',
  email: '',
  password: '',
  confirmPassword: ''
})

const loading = ref(false)
const errorMessage = ref('')
const successMessage = ref('')

const handleRegister = async () => {
  try {
    loading.value = true
    errorMessage.value = ''
    successMessage.value = ''

    if (formData.value.password !== formData.value.confirmPassword) {
      errorMessage.value = '两次输入的密码不一致'
      return
    }

    if (formData.value.password.length < 6) {
      errorMessage.value = '密码长度至少为6位'
      return
    }

    const res = await register({
      username: formData.value.username,
      phone: formData.value.phone,
      email: formData.value.email,
      password: formData.value.password
    })

    if (res.code === 200) {
      successMessage.value = '注册成功！正在跳转到登录页面...'
      setTimeout(() => {
        router.push('/login')
      }, 2000)
    } else {
      errorMessage.value = res.message || '注册失败，请稍后重试'
    }
  } catch (error) {
    console.error('注册失败:', error)
    errorMessage.value = error.response?.data?.message || '注册失败，请稍后重试'
  } finally {
    loading.value = false
  }
}
</script>
