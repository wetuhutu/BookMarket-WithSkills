<template>
  <div class="min-h-screen py-12">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
      <div class="grid grid-cols-1 lg:grid-cols-4 gap-8">
        <div class="lg:col-span-1">
          <div class="card p-6 sticky top-24">
            <div class="text-center mb-6">
              <div class="w-24 h-24 bg-primary-100 dark:bg-primary-900 rounded-full flex items-center justify-center mx-auto mb-4">
                <span class="text-4xl">{{ user.avatar }}</span>
              </div>
              <h2 class="text-xl font-bold text-gray-900 dark:text-white">{{ user.name }}</h2>
              <p class="text-gray-600 dark:text-gray-400">{{ user.school }}</p>
            </div>

            <div class="flex justify-around py-4 border-t border-b border-gray-200 dark:border-gray-700">
              <div class="text-center">
                <div class="text-2xl font-bold text-gray-900 dark:text-white">{{ user.sold }}</div>
                <div class="text-sm text-gray-600 dark:text-gray-400">已售出</div>
              </div>
              <div class="text-center">
                <div class="text-2xl font-bold text-gray-900 dark:text-white">{{ user.listed }}</div>
                <div class="text-sm text-gray-600 dark:text-gray-400">在售</div>
              </div>
              <div class="text-center">
                <div class="text-2xl font-bold text-gray-900 dark:text-white">{{ user.rating }}</div>
                <div class="text-sm text-gray-600 dark:text-gray-400">评分</div>
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
import { ref } from 'vue'

const activeTab = ref('my-books')

const user = ref({
  name: '张同学',
  avatar: '👨‍🎓',
  school: '清华大学',
  major: '计算机科学与技术',
  sold: 23,
  listed: 5,
  rating: 4.9
})

const tabs = [
  { id: 'my-books', label: '我的书籍', icon: '📚' },
  { id: 'orders', label: '我的订单', icon: '📦' },
  { id: 'favorites', label: '我的收藏', icon: '❤️' },
  { id: 'settings', label: '账户设置', icon: '⚙️' }
]

const myBooks = ref([
  {
    id: 1,
    title: '高等数学（第七版）上册',
    author: '同济大学数学系',
    price: 35,
    status: '在售',
    cover: 'https://via.placeholder.com/300x400/3b82f6/ffffff?text=高等数学'
  },
  {
    id: 2,
    title: 'JavaScript高级程序设计',
    author: 'Nicholas C. Zakas',
    price: 45,
    status: '已售出',
    cover: 'https://via.placeholder.com/300x400/10b981/ffffff?text=JavaScript'
  },
  {
    id: 3,
    title: 'Python编程：从入门到实践',
    author: 'Eric Matthes',
    price: 38,
    status: '在售',
    cover: 'https://via.placeholder.com/300x400/06b6d4/ffffff?text=Python'
  }
])

const orders = ref([
  {
    id: '202401150001',
    time: '2024-01-15 14:30',
    status: '待收货',
    bookTitle: '百年孤独',
    bookCover: 'https://via.placeholder.com/300x400/f59e0b/ffffff?text=百年孤独',
    seller: '李同学',
    price: 18
  },
  {
    id: '202401100002',
    time: '2024-01-10 09:15',
    status: '已完成',
    bookTitle: '经济学原理',
    bookCover: 'https://via.placeholder.com/300x400/8b5cf6/ffffff?text=经济学原理',
    seller: '王同学',
    price: 52
  }
])

const favorites = ref([
  {
    id: 4,
    title: '线性代数',
    price: 28,
    condition: '九成新',
    cover: 'https://via.placeholder.com/300x400/6366f1/ffffff?text=线性代数'
  },
  {
    id: 5,
    title: '活着',
    price: 15,
    condition: '全新',
    cover: 'https://via.placeholder.com/300x400/ef4444/ffffff?text=活着'
  }
])

const settings = ref({
  name: '张同学',
  school: '清华大学',
  major: '计算机科学与技术',
  phone: '',
  wechat: ''
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
