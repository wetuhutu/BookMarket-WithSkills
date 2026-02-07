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
            <div class="flex flex-col sm:flex-row justify-between items-start sm:items-center gap-4">
              <h2 class="text-2xl font-bold text-gray-900 dark:text-white">我的书籍</h2>
              <div class="flex items-center gap-4">
                <div class="flex items-center gap-2">
                  <span class="text-sm text-gray-600 dark:text-gray-400">状态：</span>
                  <select
                    v-model="myBooksPagination.status"
                    @change="handleStatusChange($event.target.value)"
                    class="input text-sm py-2"
                  >
                    <option :value="1">在售</option>
                    <option :value="0">下架</option>
                  </select>
                </div>
                <router-link to="/publish" class="btn btn-primary">
                  发布新书
                </router-link>
              </div>
            </div>

            <div v-if="myBooksLoading" class="text-center py-12">
              <div class="animate-spin rounded-full h-12 w-12 border-b-2 border-primary-600 mx-auto"></div>
              <p class="mt-4 text-gray-600 dark:text-gray-400">加载中...</p>
            </div>

            <div v-else-if="myBooksError" class="text-center py-12">
              <p class="text-red-600 dark:text-red-400 mb-4">{{ myBooksError }}</p>
              <button @click="fetchMyBooks" class="btn btn-primary text-sm">重试</button>
            </div>

            <div v-else-if="myBooks.length === 0" class="text-center py-12">
              <div class="text-6xl mb-4">📚</div>
              <p class="text-gray-600 dark:text-gray-400 mb-4">暂无书籍</p>
              <router-link to="/publish" class="btn btn-primary">
                发布第一本书
              </router-link>
            </div>

            <div v-else>
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
                        <div>
                          <span class="text-lg font-bold text-primary-600 dark:text-primary-400">¥{{ book.price }}</span>
                          <span v-if="book.originalPrice" class="text-sm text-gray-400 line-through ml-2">¥{{ book.originalPrice }}</span>
                        </div>
                        <span :class="[
                          'text-xs px-2 py-1 rounded-full',
                          book.status === 1 ? 'bg-green-100 text-green-600 dark:bg-green-900 dark:text-green-400' : 'bg-gray-100 text-gray-600 dark:bg-gray-700 dark:text-gray-400'
                        ]">
                          {{ book.status === 1 ? '在售' : '下架' }}
                        </span>
                      </div>
                      <div class="mt-2 flex items-center justify-between text-xs text-gray-500 dark:text-gray-400">
                        <span>{{ book.condition }}</span>
                        <span>库存: {{ book.stock }}</span>
                      </div>
                    </div>
                  </div>
                </div>
              </div>

              <div v-if="myBooksPagination.total > myBooksPagination.pageSize" class="mt-6 flex justify-center">
                <div class="flex items-center gap-2">
                  <button
                    @click="handlePageChange(myBooksPagination.page - 1)"
                    :disabled="myBooksPagination.page === 1"
                    :class="[
                      'px-4 py-2 rounded-lg text-sm font-medium transition-colors',
                      myBooksPagination.page === 1
                        ? 'bg-gray-100 text-gray-400 cursor-not-allowed'
                        : 'bg-white dark:bg-gray-700 text-gray-700 dark:text-gray-300 hover:bg-gray-50 dark:hover:bg-gray-600'
                    ]"
                  >
                    上一页
                  </button>
                  <span class="text-sm text-gray-600 dark:text-gray-400">
                    第 {{ myBooksPagination.page }} 页，共 {{ Math.ceil(myBooksPagination.total / myBooksPagination.pageSize) }} 页
                  </span>
                  <button
                    @click="handlePageChange(myBooksPagination.page + 1)"
                    :disabled="myBooksPagination.page >= Math.ceil(myBooksPagination.total / myBooksPagination.pageSize)"
                    :class="[
                      'px-4 py-2 rounded-lg text-sm font-medium transition-colors',
                      myBooksPagination.page >= Math.ceil(myBooksPagination.total / myBooksPagination.pageSize)
                        ? 'bg-gray-100 text-gray-400 cursor-not-allowed'
                        : 'bg-white dark:bg-gray-700 text-gray-700 dark:text-gray-300 hover:bg-gray-50 dark:hover:bg-gray-600'
                    ]"
                  >
                    下一页
                  </button>
                </div>
              </div>
            </div>
          </div>

          <div v-if="activeTab === 'orders'" class="space-y-6">
            <h2 class="text-2xl font-bold text-gray-900 dark:text-white mb-6">我的订单</h2>

            <div v-if="ordersLoading" class="text-center py-12">
              <div class="animate-spin rounded-full h-12 w-12 border-b-2 border-primary-600 mx-auto"></div>
              <p class="mt-4 text-gray-600 dark:text-gray-400">加载中...</p>
            </div>

            <div v-else-if="ordersError" class="text-center py-12">
              <p class="text-red-600 dark:text-red-400 mb-4">{{ ordersError }}</p>
              <button @click="fetchOrders" class="btn btn-primary text-sm">重试</button>
            </div>

            <div v-else-if="orders.length === 0" class="text-center py-12">
              <div class="text-6xl mb-4">📦</div>
              <p class="text-gray-600 dark:text-gray-400 mb-4">暂无订单</p>
            </div>

            <div v-else class="space-y-4">
              <div
                v-for="order in orders"
                :key="order.id"
                class="card p-6 cursor-pointer hover:shadow-lg transition-shadow"
                @click="goToOrderDetail(order.id)"
              >
                <div class="flex items-start justify-between mb-4">
                  <div>
                    <div class="text-sm text-gray-600 dark:text-gray-400 mb-1">订单号：{{ order.orderNo }}</div>
                    <div class="text-sm text-gray-600 dark:text-gray-400">{{ order.createdAt }}</div>
                  </div>
                  <span :class="[
                    'px-3 py-1 rounded-full text-sm font-medium',
                    getOrderStatusClass(order.status)
                  ]">
                    {{ getOrderStatusText(order.status) }}
                  </span>
                </div>

                <div class="flex items-center space-x-4">
                  <img :src="order.bookCover" :alt="order.bookTitle" class="w-16 h-20 object-cover rounded">
                  <div class="flex-1">
                    <h3 class="font-semibold text-gray-900 dark:text-white">{{ order.bookTitle }}</h3>
                    <p class="text-sm text-gray-600 dark:text-gray-400">卖家：{{ order.sellerName }}</p>
                    <p class="text-sm text-gray-600 dark:text-gray-400">数量：{{ order.quantity }}</p>
                  </div>
                  <div class="text-right">
                    <div class="text-lg font-bold text-gray-900 dark:text-white">¥{{ order.totalPrice }}</div>
                    <div v-if="order.quantity > 1" class="text-sm text-gray-400">单价 ¥{{ order.price }}</div>
                  </div>
                </div>

                <div class="flex justify-end space-x-4 mt-4 pt-4 border-t border-gray-200 dark:border-gray-700" @click.stop>
                  <button class="btn btn-secondary text-sm">联系卖家</button>
                  <button
                    v-if="order.status === 'pending'"
                    @click="handleCancelOrder(order.id)"
                    class="btn btn-secondary text-sm"
                  >
                    取消订单
                  </button>
                  <button
                    v-if="order.status === 'shipped'"
                    @click="handleConfirmOrder(order.id)"
                    class="btn btn-primary text-sm"
                  >
                    确认收货
                  </button>
                </div>
              </div>

              <!-- 分页 -->
              <div v-if="ordersPagination.total > ordersPagination.pageSize" class="mt-6 flex justify-center">
                <div class="flex items-center gap-2">
                  <button
                    @click="handleOrderPageChange(ordersPagination.page - 1)"
                    :disabled="ordersPagination.page === 1"
                    :class="[
                      'px-4 py-2 rounded-lg text-sm font-medium transition-colors',
                      ordersPagination.page === 1
                        ? 'bg-gray-100 text-gray-400 cursor-not-allowed'
                        : 'bg-white dark:bg-gray-700 text-gray-700 dark:text-gray-300 hover:bg-gray-50 dark:hover:bg-gray-600'
                    ]"
                  >
                    上一页
                  </button>
                  <span class="text-sm text-gray-600 dark:text-gray-400">
                    第 {{ ordersPagination.page }} 页，共 {{ Math.ceil(ordersPagination.total / ordersPagination.pageSize) }} 页
                  </span>
                  <button
                    @click="handleOrderPageChange(ordersPagination.page + 1)"
                    :disabled="ordersPagination.page >= Math.ceil(ordersPagination.total / ordersPagination.pageSize)"
                    :class="[
                      'px-4 py-2 rounded-lg text-sm font-medium transition-colors',
                      ordersPagination.page >= Math.ceil(ordersPagination.total / ordersPagination.pageSize)
                        ? 'bg-gray-100 text-gray-400 cursor-not-allowed'
                        : 'bg-white dark:bg-gray-700 text-gray-700 dark:text-gray-300 hover:bg-gray-50 dark:hover:bg-gray-600'
                    ]"
                  >
                    下一页
                  </button>
                </div>
              </div>
            </div>
          </div>

          <div v-if="activeTab === 'favorites'" class="space-y-6">
            <h2 class="text-2xl font-bold text-gray-900 dark:text-white mb-6">我的收藏</h2>

            <div v-if="favoritesLoading" class="text-center py-12">
              <div class="animate-spin rounded-full h-12 w-12 border-b-2 border-primary-600 mx-auto"></div>
              <p class="mt-4 text-gray-600 dark:text-gray-400">加载中...</p>
            </div>

            <div v-else-if="favoritesError" class="text-center py-12">
              <p class="text-red-600 dark:text-red-400 mb-4">{{ favoritesError }}</p>
              <button @click="fetchFavorites" class="btn btn-primary text-sm">重试</button>
            </div>

            <div v-else-if="favorites.length === 0" class="text-center py-12">
              <div class="text-6xl mb-4">❤️</div>
              <p class="text-gray-600 dark:text-gray-400 mb-4">暂无收藏</p>
            </div>

            <div v-else>
              <div class="grid grid-cols-2 sm:grid-cols-3 lg:grid-cols-4 gap-6">
                <div
                  v-for="book in favorites"
                  :key="book.id"
                  class="card overflow-hidden hover:shadow-lg transition-shadow cursor-pointer"
                  @click="goToBookDetail(book.bookId)"
                >
                  <div class="relative aspect-[3/4] bg-gray-200 dark:bg-gray-700">
                    <img :src="book.cover" :alt="book.title" class="w-full h-full object-cover">
                    <button
                      class="absolute top-2 right-2 p-2 bg-white dark:bg-gray-800 rounded-full shadow hover:bg-gray-100 dark:hover:bg-gray-700 transition-colors"
                      @click.stop="removeFavorite(book.bookId)"
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

              <div v-if="favoritesPagination.total > favoritesPagination.pageSize" class="mt-6 flex justify-center">
                <div class="flex items-center gap-2">
                  <button
                    @click="handleFavoritesPageChange(favoritesPagination.page - 1)"
                    :disabled="favoritesPagination.page === 1"
                    :class="[
                      'px-4 py-2 rounded-lg text-sm font-medium transition-colors',
                      favoritesPagination.page === 1
                        ? 'bg-gray-100 text-gray-400 cursor-not-allowed'
                        : 'bg-white dark:bg-gray-700 text-gray-700 dark:text-gray-300 hover:bg-gray-50 dark:hover:bg-gray-600'
                    ]"
                  >
                    上一页
                  </button>
                  <span class="text-sm text-gray-600 dark:text-gray-400">
                    第 {{ favoritesPagination.page }} 页，共 {{ Math.ceil(favoritesPagination.total / favoritesPagination.pageSize) }} 页
                  </span>
                  <button
                    @click="handleFavoritesPageChange(favoritesPagination.page + 1)"
                    :disabled="favoritesPagination.page >= Math.ceil(favoritesPagination.total / favoritesPagination.pageSize)"
                    :class="[
                      'px-4 py-2 rounded-lg text-sm font-medium transition-colors',
                      favoritesPagination.page >= Math.ceil(favoritesPagination.total / favoritesPagination.pageSize)
                        ? 'bg-gray-100 text-gray-400 cursor-not-allowed'
                        : 'bg-white dark:bg-gray-700 text-gray-700 dark:text-gray-300 hover:bg-gray-50 dark:hover:bg-gray-600'
                    ]"
                  >
                    下一页
                  </button>
                </div>
              </div>
            </div>
          </div>

          <div v-if="activeTab === 'settings'" class="space-y-6">
            <h2 class="text-2xl font-bold text-gray-900 dark:text-white mb-6">账户设置</h2>

            <div class="card p-6">
              <form @submit.prevent="handleSaveSettings" class="space-y-6">
                <div>
                  <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">头像</label>
                  <div class="flex items-center space-x-4">
                    <div class="w-20 h-20 bg-primary-100 dark:bg-primary-900 rounded-full flex items-center justify-center overflow-hidden">
                      <img v-if="settings.avatar" :src="settings.avatar" :alt="settings.name" class="w-full h-full object-cover">
                      <span v-else class="text-3xl">👤</span>
                    </div>
                    <div>
                      <input
                        type="file"
                        accept="image/*"
                        @change="handleAvatarChange"
                        class="hidden"
                        ref="avatarInput"
                      >
                      <button
                        type="button"
                        @click="$refs.avatarInput.click()"
                        :disabled="avatarUploading"
                        class="btn btn-secondary text-sm"
                      >
                        {{ avatarUploading ? '上传中...' : '上传头像' }}
                      </button>
                      <p class="text-xs text-gray-500 dark:text-gray-400 mt-1">支持 JPG、PNG 格式，最大2MB</p>
                    </div>
                  </div>
                </div>

                <div>
                  <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">昵称</label>
                  <input v-model="settings.name" type="text" class="input" placeholder="请输入昵称">
                </div>

                <div>
                  <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">邮箱</label>
                  <input v-model="settings.email" type="email" class="input" placeholder="请输入邮箱">
                </div>

                <div>
                  <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">卖家描述</label>
                  <textarea
                    v-model="settings.sellerDescription"
                    class="input"
                    rows="4"
                    placeholder="介绍一下自己，让买家更信任你"
                  ></textarea>
                  <p class="text-xs text-gray-500 dark:text-gray-400 mt-1">
                    {{ user.isSeller ? '您是认证卖家，此描述将显示给买家' : '填写此描述可申请成为卖家' }}
                  </p>
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
import { ref, onMounted, watch } from 'vue'
import { getUserProfile, updateUserProfile, getMyBooks, uploadFile, getMyOrders, getFavorites, removeFavorite as removeFavoriteApi, cancelOrder, confirmOrder } from '@/api'
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
const myBooksLoading = ref(false)
const myBooksError = ref(null)
const myBooksPagination = ref({
  page: 1,
  pageSize: 10,
  total: 0,
  status: 1
})

const orders = ref([])
const ordersLoading = ref(false)
const ordersError = ref(null)
const ordersPagination = ref({
  page: 1,
  pageSize: 10,
  total: 0,
  status: '' // 可选的状态筛选
})

const favorites = ref([])
const favoritesLoading = ref(false)
const favoritesError = ref(null)
const favoritesPagination = ref({
  page: 1,
  pageSize: 10,
  total: 0
})

const settings = ref({
  name: '',
  email: '',
  avatar: '',
  sellerDescription: ''
})

const avatarInput = ref(null)
const avatarUploading = ref(false)

const handleAvatarChange = async (event) => {
  const file = event.target.files[0]
  if (file) {
    if (file.size > 2 * 1024 * 1024) {
      alert('头像大小不能超过2MB')
      return
    }
    
    try {
      avatarUploading.value = true
      const response = await uploadFile(file, 'avatar')
      if (response.code === 200 && response.data) {
        settings.value.avatar = response.data.url
      } else {
        alert('上传失败：' + (response.message || '未知错误'))
      }
    } catch (error) {
      console.error('上传头像失败:', error)
      alert('上传失败，请稍后重试')
    } finally {
      avatarUploading.value = false
    }
  }
}

const fetchUserProfile = async () => {
  try {
    loading.value = true
    error.value = null
    const response = await getUserProfile()
    if (response.code === 200 && response.data) {
      user.value = response.data
      settings.value = {
        name: response.data.username || '',
        email: response.data.email || '',
        avatar: response.data.avatar || '',
        sellerDescription: response.data.sellerDescription || ''
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

watch(activeTab, (newTab) => {
  if (newTab === 'my-books' && myBooks.value.length === 0) {
    fetchMyBooks()
  } else if (newTab === 'orders' && orders.value.length === 0) {
    fetchOrders()
  } else if (newTab === 'favorites' && favorites.value.length === 0) {
    fetchFavorites()
  }
})

const getOrderStatusClass = (status) => {
  const normalizedStatus = {
    '待付款': 'pending',
    '待发货': 'paid', 
    '已发货': 'shipped',
    '已收货': 'received',
    '已取消': 'cancelled',
    'pending': 'pending',
    'paid': 'paid',
    'shipped': 'shipped',
    'received': 'received',
    'cancelled': 'cancelled'
  }[status] || status;

  const classes = {
    'pending': 'bg-yellow-100 text-yellow-600 dark:bg-yellow-900 dark:text-yellow-400',
    'paid': 'bg-blue-100 text-blue-600 dark:bg-blue-900 dark:text-blue-400',
    'shipped': 'bg-purple-100 text-purple-600 dark:bg-purple-900 dark:text-purple-400',
    'received': 'bg-green-100 text-green-600 dark:bg-green-900 dark:text-green-400',
    'cancelled': 'bg-gray-100 text-gray-600 dark:bg-gray-700 dark:text-gray-400'
  };
  
  return classes[normalizedStatus] || 'bg-gray-100 text-gray-600 dark:bg-gray-700 dark:text-gray-400';
};

const getOrderStatusText = (status) => {
  const statusMap = {
    'pending': '待付款',
    'paid': '待发货',
    'shipped': '已发货',
    'received': '已收货',
    'cancelled': '已取消'
  };
  return statusMap[status] || status;
};

const handleCancelOrder = async (orderId) => {
  const reason = prompt('请输入取消订单的原因（可选）')
  if (reason === null) {
    return
  }

  try {
    const response = await cancelOrder(orderId, reason)
    if (response.code === 200) {
      alert('订单已取消')
      await fetchOrders()
    } else {
      alert(response.message || '取消订单失败')
    }
  } catch (error) {
    console.error('取消订单失败:', error)
    alert(error.response?.data?.message || '取消订单失败，请稍后重试')
  }
};

const handleConfirmOrder = async (orderId) => {
  if (!confirm('确认收货后，订单将完成。确定要确认收货吗？')) {
    return
  }

  try {
    const response = await confirmOrder(orderId)
    if (response.code === 200) {
      alert('确认收货成功')
      await fetchOrders()
    } else {
      alert(response.message || '确认收货失败')
    }
  } catch (error) {
    console.error('确认收货失败:', error)
    alert(error.response?.data?.message || '确认收货失败，请稍后重试')
  }
};

const removeFavorite = async (id) => {
  try {
    const res = await removeFavoriteApi(id)
    if (res.code === 200 || res.message === '取消收藏成功') {
      alert('已取消收藏')
      await fetchFavorites()
    } else {
      alert(res.message || '取消收藏失败')
    }
  } catch (error) {
    console.error('取消收藏失败:', error)
    alert('取消收藏失败，请稍后重试')
  }
}

const fetchMyBooks = async () => {
  try {
    myBooksLoading.value = true
    myBooksError.value = null
    const params = {
      page: myBooksPagination.value.page,
      pageSize: myBooksPagination.value.pageSize,
      status: myBooksPagination.value.status
    }
    const response = await getMyBooks(params)
    if (response.code === 200 && response.data) {
      myBooks.value = response.data.list || []
      myBooksPagination.value.total = response.data.total || 0
    } else {
      myBooksError.value = '获取我的书籍失败'
    }
  } catch (err) {
    console.error('获取我的书籍失败:', err)
    myBooksError.value = '获取我的书籍失败'
    if (err.response?.status === 403) {
      router.push('/login')
    }
  } finally {
    myBooksLoading.value = false
  }
}

const handlePageChange = (page) => {
  myBooksPagination.value.page = page
  fetchMyBooks()
}

const handleStatusChange = (status) => {
  myBooksPagination.value.status = status
  myBooksPagination.value.page = 1
  fetchMyBooks()
}

const handleOrderPageChange = (page) => {
  ordersPagination.value.page = page
  fetchOrders()
}

const goToOrderDetail = (orderId) => {
  router.push(`/orders/${orderId}`)
}

const handleSaveSettings = async () => {
  try {
    const updateData = {
      username: settings.value.name,
      email: settings.value.email,
      avatar: settings.value.avatar,
      sellerDescription: settings.value.sellerDescription
    }
    
    const response = await updateUserProfile(updateData)
    
    if (response.code === 200) {
      alert('设置保存成功！')
      
      const updatedUser = {
        ...user.value,
        username: updateData.username,
        email: updateData.email,
        avatar: updateData.avatar,
        sellerDescription: updateData.sellerDescription
      }
      
      localStorage.setItem('user', JSON.stringify(updatedUser))
      user.value = updatedUser
      
      window.dispatchEvent(new Event('user-updated'))
    } else {
      alert('保存失败：' + (response.message || '未知错误'))
    }
  } catch (error) {
    console.error('保存设置失败:', error)
    alert('保存失败，请稍后重试')
  }
}

const fetchOrders = async () => {
  try {
    ordersLoading.value = true
    ordersError.value = null
    
    const params = {
      page: ordersPagination.value.page,
      pageSize: ordersPagination.value.pageSize,
      status: ordersPagination.value.status || undefined
    }
    
    const response = await getMyOrders(params)
    
    if (response.code === 200 && response.data) {
      orders.value = (response.data.list || []).map(order => ({
        id: order.id,
        orderNo: order.orderNo,
        createdAt: order.createdAt,
        status: order.status,
        bookCover: order.bookCover,
        bookTitle: order.bookTitle,
        sellerName: order.sellerName,
        price: order.price,
        totalPrice: order.totalPrice,
        quantity: order.quantity
      }))
      ordersPagination.value.total = response.data.total || 0
    } else {
      ordersError.value = response.message || '获取订单失败'
    }
  } catch (err) {
    console.error('获取订单失败:', err)
    ordersError.value = '获取订单失败，请检查登录状态'
    if (err.response?.status === 403) {
      router.push('/login')
    }
  } finally {
    ordersLoading.value = false
  }
}

const fetchFavorites = async () => {
  try {
    favoritesLoading.value = true
    favoritesError.value = null
    
    const params = {
      page: favoritesPagination.value.page,
      pageSize: favoritesPagination.value.pageSize
    }
    
    const response = await getFavorites(params)
    
    if (response.code === 200 && response.data) {
      // 处理pageSize参数不生效的问题，前端手动分页
      let allBooks = (response.data.list || []).map(item => ({
        id: item.book.id,
        bookId: item.bookId,
        title: item.book.title || '未知书名',
        author: item.book.author || '未知作者',
        cover: item.book.cover || '/default-cover.jpg',
        price: item.book.price || 0,
        originalPrice: item.book.originalPrice || 0,
        condition: item.book.condition || '未知',
        categoryId: item.book.categoryId || 'other',
        createdAt: item.createdAt
      }))
      
      // 如果后端返回的pageSize不正确，前端手动处理分页
      if (response.data.pageSize !== favoritesPagination.value.pageSize) {
        const startIndex = (favoritesPagination.value.page - 1) * favoritesPagination.value.pageSize
        const endIndex = startIndex + favoritesPagination.value.pageSize
        favorites.value = allBooks.slice(startIndex, endIndex)
      } else {
        favorites.value = allBooks
      }
      
      favoritesPagination.value.total = response.data.total || 0
    } else {
      favoritesError.value = response.message || '获取收藏列表失败'
    }
  } catch (err) {
    console.error('获取收藏列表失败:', err)
    favoritesError.value = '获取收藏列表失败，请检查登录状态'
    if (err.response?.status === 403) {
      router.push('/login')
    }
  } finally {
    favoritesLoading.value = false
  }
}

const handleFavoritesPageChange = (page) => {
  favoritesPagination.value.page = page
  fetchFavorites()
}

const goToBookDetail = (bookId) => {
  router.push(`/books/${bookId}`)
}


</script>
