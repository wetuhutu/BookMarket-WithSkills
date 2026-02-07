<template>
  <div class="min-h-screen py-12">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
      <div v-if="loading" class="flex justify-center items-center py-20">
        <div class="animate-spin rounded-full h-12 w-12 border-b-2 border-primary-600"></div>
      </div>

      <div v-else-if="book" class="grid grid-cols-1 lg:grid-cols-2 gap-12">
        <div>
          <div class="card p-8">
            <img
              :src="book.cover"
              :alt="book.title"
              class="w-full rounded-lg shadow-lg"
            >
          </div>
        </div>

        <div class="space-y-6">
          <div>
            <span class="inline-block bg-primary-100 dark:bg-primary-900 text-primary-600 dark:text-primary-400 text-sm px-3 py-1 rounded-full mb-4">
              {{ book.categoryId }}
            </span>
            <h1 class="text-3xl font-bold text-gray-900 dark:text-white mb-2">{{ book.title }}</h1>
            <p class="text-lg text-gray-600 dark:text-gray-400">{{ book.author }} / {{ book.publisher }}</p>
          </div>

          <div class="flex items-baseline space-x-4">
            <span class="text-4xl font-bold text-primary-600 dark:text-primary-400">¥{{ book.price }}</span>
            <span class="text-xl text-gray-400 line-through">¥{{ book.originalPrice }}</span>
            <span class="text-sm text-green-600 dark:text-green-400">
              省 ¥{{ book.originalPrice - book.price }} ({{ Math.round((1 - book.price / book.originalPrice) * 100) }}%)
            </span>
          </div>

          <div class="grid grid-cols-2 gap-4">
            <div class="card p-4">
              <div class="text-sm text-gray-600 dark:text-gray-400 mb-1">成色</div>
              <div class="font-semibold text-gray-900 dark:text-white">{{ book.condition }}</div>
            </div>
            <div class="card p-4">
              <div class="text-sm text-gray-600 dark:text-gray-400 mb-1">发布时间</div>
              <div class="font-semibold text-gray-900 dark:text-white">{{ formatRelativeTime(book.createdAt) }}</div>
            </div>
            <div class="card p-4">
              <div class="text-sm text-gray-600 dark:text-gray-400 mb-1">页数</div>
              <div class="font-semibold text-gray-900 dark:text-white">{{ book.pages }}</div>
            </div>
            <div class="card p-4">
              <div class="text-sm text-gray-600 dark:text-gray-400 mb-1">ISBN</div>
              <div class="font-semibold text-gray-900 dark:text-white">{{ book.isbn }}</div>
            </div>
            <div class="card p-4">
              <div class="text-sm text-gray-600 dark:text-gray-400 mb-1">出版时间</div>
              <div class="font-semibold text-gray-900 dark:text-white">{{ book.publishDate }}</div>
            </div>
          </div>

          <div class="card p-6">
            <h3 class="font-semibold text-gray-900 dark:text-white mb-4">卖家信息</h3>
            <div class="flex items-center space-x-4">
              <div class="w-12 h-12 bg-primary-100 dark:bg-primary-900 rounded-full flex items-center justify-center">
                <span class="text-xl">👨‍🎓</span>
              </div>
              <div class="flex-1">
                <div class="font-semibold text-gray-900 dark:text-white">{{ book.sellerName }}</div>
                <div class="text-sm text-gray-600 dark:text-gray-400">
                  {{ book.sellerLevel }} {{ book.verified ? '· 已认证' : '' }}
                </div>
              </div>
              <div class="text-right">
                <div class="text-sm text-gray-600 dark:text-gray-400">信用评分</div>
                <div class="font-semibold text-yellow-500">⭐ {{ book.sellerRating }}</div>
              </div>
            </div>
          </div>

          <div class="flex space-x-4">
            <button 
              class="flex-1 btn btn-primary text-lg py-3" 
              :disabled="purchasing"
              @click="handlePurchase"
            >
              {{ purchasing ? '处理中...' : '立即购买' }}
            </button>
            <button class="flex-1 btn btn-secondary text-lg py-3">
              联系卖家
            </button>
            <button class="btn btn-secondary px-6" @click="toggleWishlist">
              <svg v-if="!isWishlisted" class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z"></path>
              </svg>
              <svg v-else class="w-6 h-6 text-red-500" fill="currentColor" viewBox="0 0 24 24">
                <path d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z"></path>
              </svg>
            </button>
          </div>
        </div>
      </div>

      <div v-if="book" class="mt-12">
        <div class="card p-8">
          <h2 class="text-2xl font-bold text-gray-900 dark:text-white mb-6">书籍详情</h2>
          <div class="prose dark:prose-invert max-w-none">
            <p class="text-gray-700 dark:text-gray-300 mb-4">
              {{ book.description }}
            </p>
            <div class="grid grid-cols-2 md:grid-cols-4 gap-4 mt-8">
              <div>
                <div class="text-sm text-gray-600 dark:text-gray-400">ISBN</div>
                <div class="font-medium text-gray-900 dark:text-white">{{ book.isbn }}</div>
              </div>
              <div>
                <div class="text-sm text-gray-600 dark:text-gray-400">出版时间</div>
                <div class="font-medium text-gray-900 dark:text-white">{{ book.publishDate }}</div>
              </div>
              <div>
                <div class="text-sm text-gray-600 dark:text-gray-400">页数</div>
                <div class="font-medium text-gray-900 dark:text-white">{{ book.pages }}</div>
              </div>
              <div>
                <div class="text-sm text-gray-600 dark:text-gray-400">装帧</div>
                <div class="font-medium text-gray-900 dark:text-white">{{ book.binding }}</div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div v-if="book" class="mt-12">
        <div class="card p-8">
          <h2 class="text-2xl font-bold text-gray-900 dark:text-white mb-6">相似推荐</h2>
          <div class="grid grid-cols-2 sm:grid-cols-3 lg:grid-cols-4 gap-6">
            <div
              v-for="item in relatedBooks"
              :key="item.id"
              class="cursor-pointer hover:shadow-lg transition-shadow"
              @click="goToDetail(item.id)"
            >
              <div class="aspect-[3/4] bg-gray-200 dark:bg-gray-700 rounded-lg overflow-hidden mb-3">
                <img :src="item.cover" :alt="item.title" class="w-full h-full object-cover">
              </div>
              <h3 class="font-medium text-gray-900 dark:text-white line-clamp-2">{{ item.title }}</h3>
              <div class="flex items-center justify-between mt-2">
                <span class="font-bold text-primary-600 dark:text-primary-400">¥{{ item.price }}</span>
                <span class="text-sm text-gray-400 line-through">¥{{ item.originalPrice }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getBookDetail, getBookReviews, getBookReviewsStatistics, getBookRelated, addFavorite, removeFavorite, getFavorites, createOrder } from '@/api/index'
import { formatRelativeTime } from '@/utils/format'

const route = useRoute()
const router = useRouter()

const loading = ref(true)
const purchasing = ref(false)
const isWishlisted = ref(false)
const book = ref(null)
const reviews = ref([])
const statistics = ref(null)
const relatedBooks = ref([])

const fetchBookDetail = async () => {
  try {
    const res = await getBookDetail(route.params.id)
    if (res.code === 200) {
      book.value = res.data
    }
  } catch (error) {
    console.error('获取书籍详情失败:', error)
  }
}

const fetchReviews = async () => {
  try {
    const res = await getBookReviews(route.params.id, { page: 1, pageSize: 10 })
    if (res.code === 200) {
      reviews.value = res.data.list
    }
  } catch (error) {
    console.error('获取评价失败:', error)
  }
}

const fetchStatistics = async () => {
  try {
    const res = await getBookReviewsStatistics(route.params.id)
    if (res.code === 200) {
      statistics.value = res.data
    }
  } catch (error) {
    console.error('获取评价统计失败:', error)
  }
}

const fetchRelated = async () => {
  try {
    const res = await getBookRelated(route.params.id, 4)
    if (res.code === 200) {
      relatedBooks.value = res.data
    }
  } catch (error) {
    console.error('获取相似推荐失败:', error)
  }
}

const checkFavoriteStatus = async () => {
  try {
    const res = await getFavorites({ page: 1, pageSize: 100 })
    if (res.code === 200 && res.data) {
      const favoriteBooks = res.data.list || []
      isWishlisted.value = favoriteBooks.some(item => item.bookId === parseInt(route.params.id))
    }
  } catch (error) {
    console.error('检查收藏状态失败:', error)
  }
}

const toggleWishlist = async () => {
  try {
    if (isWishlisted.value) {
      const res = await removeFavorite(route.params.id)
      if (res.code === 200 || res.message === '取消收藏成功') {
        isWishlisted.value = false
        alert('已取消收藏')
      } else {
        alert(res.message || '取消收藏失败')
      }
    } else {
      const res = await addFavorite(route.params.id)
      if (res.code === 200 || res.message === '收藏成功') {
        isWishlisted.value = true
        alert('收藏成功')
      } else if (res.message === 'Duplicate') {
        alert('该书籍已在收藏列表中')
        isWishlisted.value = true
      } else {
        alert(res.message || '收藏失败')
      }
    }
  } catch (error) {
    console.error('收藏操作失败:', error)
    alert('操作失败，请稍后重试')
  }
}

const handlePurchase = async () => {
  if (!book.value) {
    alert('书籍信息加载失败')
    return
  }

  const token = localStorage.getItem('token')
  if (!token) {
    alert('请先登录')
    router.push('/login')
    return
  }

  if (book.value.stock <= 0) {
    alert('该书籍已售罄')
    return
  }

  try {
    purchasing.value = true
    const orderData = {
      bookId: book.value.id,
      quantity: 1,
      sellerId: book.value.sellerId
    }

    const response = await createOrder(orderData)

    if (response.code === 200 && response.data) {
      alert('订单创建成功！')
      router.push(`/orders/${response.data.id}`)
    } else {
      alert(response.message || '订单创建失败')
    }
  } catch (error) {
    console.error('创建订单失败:', error)
    if (error.response?.status === 401) {
      alert('登录已过期，请重新登录')
      router.push('/login')
    } else {
      alert(error.response?.data?.message || '订单创建失败，请稍后重试')
    }
  } finally {
    purchasing.value = false
  }
}

const goToDetail = (id) => {
  router.push(`/books/${id}`)
}

onMounted(async () => {
  await Promise.all([
    fetchBookDetail(),
    fetchReviews(),
    fetchStatistics(),
    fetchRelated()
  ])
  await checkFavoriteStatus()
  loading.value = false
})
</script>
