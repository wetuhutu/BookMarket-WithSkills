<template>
  <div class="min-h-screen py-12">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
      <div class="grid grid-cols-1 lg:grid-cols-4 gap-8">
        <div class="lg:col-span-1">
          <div class="card p-6 sticky top-24">
            <div v-if="loading" class="text-center py-8">
              <div class="animate-spin rounded-full h-12 w-12 border-b-2 border-primary-600 mx-auto"></div>
              <p class="mt-4 text-gray-600 dark:text-gray-400">加载中...</p>
            </div>
            <div v-else-if="error" class="text-center py-8">
              <p class="text-red-600 dark:text-red-400">{{ error }}</p>
              <button @click="fetchUserProfile" class="mt-4 btn btn-primary text-sm">重试</button>
            </div>
            <div v-else>
              <div class="text-center mb-6">
                <div class="w-24 h-24 bg-primary-100 dark:bg-primary-900 rounded-full flex items-center justify-center mx-auto mb-4 overflow-hidden">
                  <img v-if="user.avatar" :src="user.avatar" :alt="user.username" class="w-full h-full object-cover">
                  <span v-else class="text-4xl">👤</span>
                </div>
                <h2 class="text-xl font-bold text-gray-900 dark:text-white">{{ user.username }}</h2>
                <p class="text-gray-600 dark:text-gray-400">{{ user.level }}</p>
              </div>

              <div class="flex justify-around py-4 border-t border-b border-gray-200 dark:border-gray-700">
                <div class="text-center">
                  <div class="text-2xl font-bold text-gray-900 dark:text-white">{{ user.sellerSalesCount }}</div>
                  <div class="text-sm text-gray-600 dark:text-gray-400">已售出</div>
                </div>
                <div class="text-center">
                  <div class="text-2xl font-bold text-gray-900 dark:text-white">{{ user.points }}</div>
                  <div class="text-sm text-gray-600 dark:text-gray-400">积分</div>
                </div>
                <div class="text-center">
                  <div class="text-2xl font-bold text-gray-900 dark:text-white">{{ user.sellerRating || 0 }}</div>
                  <div class="text-sm text-gray-600 dark:text-gray-400">评分</div>
                </div>
              </div>

              <div v-if="user.isSeller" class="mt-4 p-3 bg-yellow-50 dark:bg-yellow-900/20 rounded-lg">
                <div class="flex items-center justify-between mb-2">
                  <span class="text-sm font-medium text-gray-700 dark:text-gray-300">卖家等级</span>
                  <span class="text-sm font-bold text-yellow-600 dark:text-yellow-400">{{ user.sellerLevel || '普通卖家' }}</span>
                </div>
                <div class="flex items-center justify-between mb-2">
                  <span class="text-sm font-medium text-gray-700 dark:text-gray-300">好评率</span>
                  <span class="text-sm font-bold text-green-600 dark:text-green-400">{{ user.sellerPositiveRate }}%</span>
                </div>
                <div v-if="user.sellerIsVerified" class="flex items-center justify-center mt-2">
                  <svg class="w-4 h-4 text-blue-500 mr-1" fill="currentColor" viewBox="0 0 20 20">
                    <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clip-rule="evenodd"></path>
                  </svg>
                  <span class="text-sm text-blue-600 dark:text-blue-400">已认证卖家</span>
                </div>
              </div>

              <nav class="mt-6 space-y-2">
                <button
                  v-for="tab in tabs"
                  :key="tab.id"
                  @click="activeTab = tab.id"
                  :class="[
                    'w-full text-left px-4 py-3 rounded-lg transition-colors',
                    activeTab === tab.id
                      ? 'bg-primary-100 dark:bg-primary-900 text-primary-600 dark:text-primary-400 font-medium'
                      : 'text-gray-700 dark:text-gray-300 hover:bg-gray-100 dark:hover:bg-gray-700'
                  ]"
                >
                  <span class="mr-2">{{ tab.icon }}</span>
                  {{ tab.label }}
                </button>
              </nav>
            </div>
          </div>
        </div>

        <div class="lg:col-span-3">
          <div v-if="activeTab === 'my-books'" class="space-y-6">
            <div class="flex justify-between items-center">
              <h2 class="text-2xl font-bold text-gray-900 dark:text-white">我的书籍</h2>
              <router-link to="/publish" class="btn btn-primary">
                发布新书
              </router-link>
            </div>

            <div class="grid grid-cols-1 sm:grid-cols-2 gap-6">
              <div
                v-for="book in myBooks"
                :key="book.id"
                class="card overflow-hidden hover:shadow-lg transition-shadow"
              >
                <div class="flex">
                  <div class="w-32 flex-shrink-0">
                    <img :src="book.cover" :alt="book.title" class="w-full h-full object-cover">
                  </div>
                  <div class="flex-1 p-4">
                    <h3 class="font-semibold text-gray-900 dark:text-white mb-1 line-clamp-2">{{ book.title }}</h3>
                    <p class="text-sm text-gray-600 dark:text-gray-400 mb-2">{{ book.author }}</p>
                    <div class="flex items-center justify-between">
                      <span class="text-lg font-bold text-primary-600 dark:text-primary-400">¥{{ book.price }}</span>
                      <span :class="[
                        'text-xs px-2 py-1 rounded-full',
                        book.status === '在售' ? 'bg-green-100 text-green-600' : 'bg-gray-100 text-gray-600'
                      ]">
                        {{ book.status }}
                      </span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <div v-if="activeTab === 'orders'" class="space-y-6">
            <h2 class="text-2xl font-bold text-gray-900 dark:text-white mb-6">我的订单</h2>

            <div class="space-y-4">
              <div
                v-for="order in orders"
                :key="order.id"
                class="card p-6"
              >
                <div class="flex items-start justify-between mb-4">
                  <div>
                    <div class="text-sm text-gray-600 dark:text-gray-400 mb-1">订单号：{{ order.id }}</div>
                    <div class="text-sm text-gray-600 dark:text-gray-400">{{ order.time }}</div>
                  </div>
                  <span :class="[
                    'px-3 py-1 rounded-full text-sm font-medium',
                    getOrderStatusClass(order.status)
                  ]">
                    {{ order.status }}
                  </span>
                </div>

                <div class="flex items-center space-x-4">
                  <img :src="order.bookCover" :alt="order.bookTitle" class="w-16 h-20 object-cover rounded">
                  <div class="flex-1">
                    <h3 class="font-semibold text-gray-900 dark:text-white">{{ order.bookTitle }}</h3>
                    <p class="text-sm text-gray-600 dark:text-gray-400">{{ order.seller }}</p>
                  </div>
                  <div class="text-right">
                    <div class="text-lg font-bold text-gray-900 dark:text-white">¥{{ order.price }}</div>
                  </div>
                </div>

                <div class="flex justify-end space-x-4 mt-4 pt-4 border-t border-gray-200 dark:border-gray-700">
                  <button class="btn btn-secondary text-sm">联系卖家</button>
                  <button
                    v-if="order.status === '待收货'"
                    class="btn btn-primary text-sm"
                  >
                    确认收货
                  </button>
                </div>
              </div>
            </div>
          </div>

          <div v-if="activeTab === 'favorites'" class="space-y-6">
            <h2 class="text-2xl font-bold text-gray-900 dark:text-white mb-6">我的收藏</h2>

            <div class="grid grid-cols-2 sm:grid-cols-3 lg:grid-cols-4 gap-6">
              <div
                v-for="book in favorites"
                :key="book.id"
                class="card overflow-hidden hover:shadow-lg transition-shadow cursor-pointer"
              >
                <div class="relative aspect-[3/4] bg-gray-200 dark:bg-gray-700">
                  <img :src="book.cover" :alt="book.title" class="w-full h-full object-cover">
                  <button
                    class="absolute top-2 right-2 p-2 bg-white dark:bg-gray-800 rounded-full shadow hover:bg-gray-100 dark:hover:bg-gray-700 transition-colors"
                    @click.stop="removeFavorite(book.id)"
                  >
                    <svg class="w-4 h-4 text-red-500" fill="currentColor" viewBox="0 0 24 24">
                      <path d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z"></path>
                    </svg>
                  </button>
                </div>
                <div class="p-4">
                  <h3 class="font-medium text-gray-900 dark:text-white line-clamp-2 mb-2">{{ book.title }}</h3>
                  <div class="flex items-center justify-between">
                    <span class="font-bold text-primary-600 dark:text-primary-400">¥{{ book.price }}</span>
                    <span class="text-xs text-gray-500 dark:text-gray-400">{{ book.condition }}</span>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <div v-if="activeTab === 'settings'" class="space-y-6">
            <h2 class="text-2xl font-bold text-gray-900 dark:text-white mb-6">账户设置</h2>

            <div class="card p-6">
              <form @submit.prevent="handleSaveSettings" class="space-y-6">
                <div>
                  <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">昵称</label>
                  <input v-model="settings.name" type="text" class="input">
                </div>

                <div>
                  <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">学校</label>
                  <input v-model="settings.school" type="text" class="input">
                </div>

                <div>
                  <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">专业</label>
                  <input v-model="settings.major" type="text" class="input">
                </div>

                <div>
                  <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">手机号</label>
                  <input v-model="settings.phone" type="tel" class="input">
                </div>

                <div>
                  <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">微信号</label>
                  <input v-model="settings.wechat" type="text" class="input">
                </div>

                <button type="submit" class="btn btn-primary">保存设置</button>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getUserProfile } from '@/api'
import { useRouter } from 'vue-router'

const router = useRouter()
const activeTab = ref('my-books')
const loading = ref(true)
const error = ref(null)

const user = ref({
  id: null,
  username: '',
  phone: '',
  email: '',
  avatar: '',
  level: '',
  points: 0,
  isSeller: 0,
  sellerLevel: null,
  sellerRating: 0,
  sellerIsVerified: 0,
  sellerDescription: null,
  sellerPositiveRate: 0,
  sellerSalesCount: 0,
  createdAt: '',
  updatedAt: ''
})

const tabs = [
  { id: 'my-books', label: '我的书籍', icon: '📚' },
  { id: 'orders', label: '我的订单', icon: '📦' },
  { id: 'favorites', label: '我的收藏', icon: '❤️' },
  { id: 'settings', label: '账户设置', icon: '⚙️' }
]

const myBooks = ref([])

const orders = ref([])

const favorites = ref([])

const settings = ref({
  name: '',
  school: '',
  major: '',
  phone: '',
  wechat: ''
})

const fetchUserProfile = async () => {
  try {
    loading.value = true
    error.value = null
    const response = await getUserProfile()
    if (response.code === 200 && response.data) {
      user.value = response.data
      settings.value = {
        name: response.data.username || '',
        school: '',
        major: '',
        phone: response.data.phone || '',
        wechat: ''
      }
    } else {
      error.value = '获取用户信息失败'
    }
  } catch (err) {
    console.error('获取用户信息失败:', err)
    error.value = '获取用户信息失败，请检查登录状态'
    if (err.response?.status === 403) {
      router.push('/login')
    }
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  fetchUserProfile()
})

const getOrderStatusClass = (status) => {
  const classes = {
    '待收货': 'bg-yellow-100 text-yellow-600',
    '已完成': 'bg-green-100 text-green-600',
    '已取消': 'bg-gray-100 text-gray-600'
  }
  return classes[status] || 'bg-gray-100 text-gray-600'
}

const removeFavorite = (id) => {
  favorites.value = favorites.value.filter(book => book.id !== id)
}

const handleSaveSettings = () => {
  alert('设置保存成功！')
}
</script>
