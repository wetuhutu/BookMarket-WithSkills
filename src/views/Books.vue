<template>
  <div class="min-h-screen py-12">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
      <div class="mb-8">
        <h1 class="text-3xl font-bold text-gray-900 dark:text-white mb-6">书籍列表</h1>
        
        <div class="flex flex-col md:flex-row gap-4">
          <div class="flex-1">
            <div class="relative">
              <input
                v-model="searchQuery"
                type="text"
                placeholder="搜索书名、作者、ISBN..."
                class="input pl-10"
                @input="handleSearch"
              >
              <svg class="absolute left-3 top-1/2 transform -translate-y-1/2 w-5 h-5 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"></path>
              </svg>
            </div>
          </div>
          <select v-model="selectedCategory" class="input md:w-48" @change="handleFilter">
            <option value="">全部分类</option>
            <option v-for="cat in categories" :key="cat" :value="cat">{{ cat }}</option>
          </select>
          <select v-model="selectedCondition" class="input md:w-48" @change="handleFilter">
            <option value="">全部成色</option>
            <option value="全新">全新</option>
            <option value="九成新">九成新</option>
            <option value="八成新">八成新</option>
            <option value="七成新">七成新</option>
          </select>
          <select v-model="sortBy" class="input md:w-48" @change="handleSort">
            <option value="default">默认排序</option>
            <option value="price-asc">价格从低到高</option>
            <option value="price-desc">价格从高到低</option>
            <option value="time-desc">最新发布</option>
          </select>
        </div>
      </div>

      <div v-if="loading" class="flex justify-center items-center py-20">
        <div class="animate-spin rounded-full h-12 w-12 border-b-2 border-primary-600"></div>
      </div>

      <div v-else-if="books.length === 0" class="text-center py-20">
        <svg class="mx-auto h-24 w-24 text-gray-400 mb-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 6.253v13m0-13C10.832 5.477 9.246 5 7.5 5S4.168 5.477 3 6.253v13C4.168 18.477 5.754 18 7.5 18s3.332.477 4.5 1.253m0-13C13.168 5.477 14.754 5 16.5 5c1.747 0 3.332.477 4.5 1.253v13C19.832 18.477 18.247 18 16.5 18c-1.746 0-3.332.477-4.5 1.253"></path>
        </svg>
        <h3 class="text-xl font-medium text-gray-900 dark:text-white mb-2">暂无相关书籍</h3>
        <p class="text-gray-600 dark:text-gray-400">试试其他关键词或筛选条件</p>
      </div>

      <div v-else class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
        <div
          v-for="book in books"
          :key="book.id"
          class="card overflow-hidden hover:shadow-lg transition-shadow cursor-pointer group"
          @click="goToDetail(book.id)"
        >
          <div class="relative aspect-[3/4] bg-gray-200 dark:bg-gray-700 overflow-hidden">
            <img
              :src="book.cover"
              :alt="book.title"
              class="w-full h-full object-cover group-hover:scale-105 transition-transform duration-300"
            >
            <div class="absolute top-2 right-2 bg-primary-600 text-white text-xs px-2 py-1 rounded-full">
              {{ book.condition }}
            </div>
          </div>
          <div class="p-4">
            <h3 class="font-semibold text-gray-900 dark:text-white mb-1 line-clamp-2">{{ book.title }}</h3>
            <p class="text-sm text-gray-600 dark:text-gray-400 mb-2">{{ book.author }}</p>
            <div class="flex items-center justify-between">
              <div>
                <span class="text-lg font-bold text-primary-600 dark:text-primary-400">¥{{ book.price }}</span>
                <span class="text-sm text-gray-400 line-through ml-2">¥{{ book.originalPrice }}</span>
              </div>
              <span class="text-xs text-gray-500 dark:text-gray-400">{{ formatRelativeTime(book.createdAt) }}</span>
            </div>
          </div>
        </div>
      </div>

      <div v-if="books.length > 0" class="mt-12 flex justify-center">
        <div class="flex space-x-2">
          <button
            v-for="page in totalPages"
            :key="page"
            @click="currentPage = page"
            :class="[
              'px-4 py-2 rounded-lg font-medium transition-colors',
              currentPage === page
                ? 'bg-primary-600 text-white'
                : 'bg-white dark:bg-gray-800 text-gray-700 dark:text-gray-200 hover:bg-gray-100 dark:hover:bg-gray-700'
            ]"
          >
            {{ page }}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue'
import { useRouter } from 'vue-router'
import { getBooks, getCategories } from '@/api/index'
import { formatRelativeTime } from '@/utils/format'

const router = useRouter()

const searchQuery = ref('')
const selectedCategory = ref('')
const selectedCondition = ref('')
const sortBy = ref('default')
const currentPage = ref(1)
const loading = ref(true)
const books = ref([])
const categories = ref([])
const total = ref(0)
const pageSize = ref(12)

const categoryMap = {
  '教育': 'education',
  '艺术': 'art',
  '历史': 'history',
  '小说': 'novel',
  '哲学': 'philosophy',
  '科学': 'science'
}

const fetchBooks = async () => {
  try {
    loading.value = true
    const params = {
      page: currentPage.value,
      pageSize: pageSize.value
    }
    
    if (searchQuery.value) {
      params.keyword = searchQuery.value
    }
    if (selectedCategory.value) {
      params.category = categoryMap[selectedCategory.value] || selectedCategory.value
    }
    if (selectedCondition.value) {
      params.condition = selectedCondition.value
    }
    if (sortBy.value !== 'default') {
      params.sortBy = sortBy.value
    }
    
    const res = await getBooks(params)
    if (res.code === 200) {
      books.value = res.data.list
      total.value = res.data.total
    }
  } catch (error) {
    console.error('获取书籍列表失败:', error)
  } finally {
    loading.value = false
  }
}

const fetchCategories = async () => {
  try {
    const res = await getCategories()
    if (res.code === 200) {
      categories.value = res.data.map(cat => cat.name)
    }
  } catch (error) {
    console.error('获取分类失败:', error)
  }
}

const handleSearch = () => {
  currentPage.value = 1
  fetchBooks()
}

const handleFilter = () => {
  currentPage.value = 1
  fetchBooks()
}

const handleSort = () => {
  currentPage.value = 1
  fetchBooks()
}

const goToDetail = (id) => {
  router.push(`/books/${id}`)
}

const totalPages = computed(() => Math.ceil(total.value / pageSize.value))

watch([currentPage], () => {
  fetchBooks()
})

onMounted(() => {
  fetchCategories()
  fetchBooks()
})
</script>
