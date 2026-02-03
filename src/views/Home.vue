<template>
  <div>
    <section class="relative bg-gradient-to-br from-primary-50 to-secondary-50 dark:from-gray-900 dark:to-gray-800 overflow-hidden">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-20 lg:py-32">
        <div class="grid grid-cols-1 lg:grid-cols-2 gap-12 items-center">
          <div class="space-y-8">
            <h1 class="text-4xl sm:text-5xl lg:text-6xl font-bold text-gray-900 dark:text-white leading-tight">
              让闲置书籍
              <span class="text-primary-600 dark:text-primary-400">流动起来</span>
            </h1>
            <p class="text-xl text-gray-600 dark:text-gray-300">
              专为校园师生打造的二手书交易平台，轻松买卖，传递知识与价值
            </p>
            <div class="flex flex-col sm:flex-row gap-4">
              <router-link to="/books" class="btn btn-primary text-lg px-8 py-3">
                浏览书籍
              </router-link>
              <router-link to="/publish" class="btn btn-secondary text-lg px-8 py-3">
                发布书籍
              </router-link>
            </div>
            <div class="flex items-center space-x-8 pt-4">
              <div class="text-center">
                <div class="text-3xl font-bold text-gray-900 dark:text-white">10000+</div>
                <div class="text-sm text-gray-600 dark:text-gray-400">在售书籍</div>
              </div>
              <div class="text-center">
                <div class="text-3xl font-bold text-gray-900 dark:text-white">5000+</div>
                <div class="text-sm text-gray-600 dark:text-gray-400">活跃用户</div>
              </div>
              <div class="text-center">
                <div class="text-3xl font-bold text-gray-900 dark:text-white">98%</div>
                <div class="text-sm text-gray-600 dark:text-gray-400">好评率</div>
              </div>
            </div>
          </div>
          <div class="relative">
            <div class="bg-white dark:bg-gray-800 rounded-2xl shadow-2xl p-8 transform rotate-3 hover:rotate-0 transition-transform duration-300">
              <div class="flex items-center space-x-4 mb-6">
                <div class="w-16 h-16 bg-primary-100 dark:bg-primary-900 rounded-full flex items-center justify-center">
                  <svg class="w-8 h-8 text-primary-600 dark:text-primary-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 6.253v13m0-13C10.832 5.477 9.246 5 7.5 5S4.168 5.477 3 6.253v13C4.168 18.477 5.754 18 7.5 18s3.332.477 4.5 1.253m0-13C13.168 5.477 14.754 5 16.5 5c1.747 0 3.332.477 4.5 1.253v13C19.832 18.477 18.247 18 16.5 18c-1.746 0-3.332.477-4.5 1.253"></path>
                  </svg>
                </div>
                <div>
                  <div class="font-semibold text-gray-900 dark:text-white">高等数学</div>
                  <div class="text-sm text-gray-600 dark:text-gray-400">同济大学出版社</div>
                </div>
              </div>
              <div class="space-y-3">
                <div class="flex justify-between">
                  <span class="text-gray-600 dark:text-gray-400">原价</span>
                  <span class="text-gray-400 dark:text-gray-500 line-through">¥68.00</span>
                </div>
                <div class="flex justify-between">
                  <span class="text-gray-600 dark:text-gray-400">现价</span>
                  <span class="text-2xl font-bold text-primary-600 dark:text-primary-400">¥35.00</span>
                </div>
                <div class="flex justify-between">
                  <span class="text-gray-600 dark:text-gray-400">成色</span>
                  <span class="text-green-600 dark:text-green-400">九成新</span>
                </div>
              </div>
              <button class="w-full mt-6 btn btn-primary">立即购买</button>
            </div>
          </div>
        </div>
      </div>
    </section>

    <section class="py-20 bg-white dark:bg-gray-800">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="text-center mb-16">
          <h2 class="text-3xl sm:text-4xl font-bold text-gray-900 dark:text-white mb-4">为什么选择我们</h2>
          <p class="text-xl text-gray-600 dark:text-gray-400">安全、便捷、高效的校园二手书交易体验</p>
        </div>
        <div class="grid grid-cols-1 md:grid-cols-3 gap-8">
          <div class="card p-8 text-center hover:shadow-lg transition-shadow">
            <div class="w-16 h-16 bg-primary-100 dark:bg-primary-900 rounded-full flex items-center justify-center mx-auto mb-6">
              <svg class="w-8 h-8 text-primary-600 dark:text-primary-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m5.618-4.016A11.955 11.955 0 0112 2.944a11.955 11.955 0 01-8.618 3.04A12.02 12.02 0 003 9c0 5.591 3.824 10.29 9 11.622 5.176-1.332 9-6.03 9-11.622 0-1.042-.133-2.052-.382-3.016z"></path>
              </svg>
            </div>
            <h3 class="text-xl font-semibold text-gray-900 dark:text-white mb-3">安全交易</h3>
            <p class="text-gray-600 dark:text-gray-400">实名认证，担保交易，让每一次买卖都安心无忧</p>
          </div>
          <div class="card p-8 text-center hover:shadow-lg transition-shadow">
            <div class="w-16 h-16 bg-secondary-100 dark:bg-secondary-900 rounded-full flex items-center justify-center mx-auto mb-6">
              <svg class="w-8 h-8 text-secondary-600 dark:text-secondary-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 10V3L4 14h7v7l9-11h-7z"></path>
              </svg>
            </div>
            <h3 class="text-xl font-semibold text-gray-900 dark:text-white mb-3">快速便捷</h3>
            <p class="text-gray-600 dark:text-gray-400">校内交易，当面交付，省时省力，即时到账</p>
          </div>
          <div class="card p-8 text-center hover:shadow-lg transition-shadow">
            <div class="w-16 h-16 bg-purple-100 dark:bg-purple-900 rounded-full flex items-center justify-center mx-auto mb-6">
              <svg class="w-8 h-8 text-purple-600 dark:text-purple-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path>
              </svg>
            </div>
            <h3 class="text-xl font-semibold text-gray-900 dark:text-white mb-3">价格实惠</h3>
            <p class="text-gray-600 dark:text-gray-400">二手好价，物超所值，让知识传递更轻松</p>
          </div>
        </div>
      </div>
    </section>

    <section class="py-20 bg-gray-50 dark:bg-gray-900">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="text-center mb-16">
          <h2 class="text-3xl sm:text-4xl font-bold text-gray-900 dark:text-white mb-4">热门书籍分类</h2>
          <p class="text-xl text-gray-600 dark:text-gray-400">找到你需要的书籍</p>
        </div>
        <div class="grid grid-cols-2 sm:grid-cols-3 lg:grid-cols-6 gap-4">
          <div v-for="category in categories" :key="category.name" class="card p-6 text-center cursor-pointer hover:shadow-lg transition-all hover:-translate-y-1">
            <div class="text-4xl mb-3">{{ category.icon }}</div>
            <div class="font-medium text-gray-900 dark:text-white">{{ category.name }}</div>
            <div class="text-sm text-gray-600 dark:text-gray-400 mt-1">{{ category.count }} 本</div>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getCategories } from '@/api/index'

const categories = ref([])
const loading = ref(true)

const fetchCategories = async () => {
  try {
    const res = await getCategories()
    if (res.code === 200) {
      categories.value = res.data
    }
  } catch (error) {
    console.error('获取分类失败:', error)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  fetchCategories()
})
</script>
