<template>
  <div class="min-h-screen py-12">
    <div class="max-w-3xl mx-auto px-4 sm:px-6 lg:px-8">
      <div class="mb-8">
        <h1 class="text-3xl font-bold text-gray-900 dark:text-white mb-2">发布书籍</h1>
        <p class="text-gray-600 dark:text-gray-400">填写书籍信息，让更多人看到你的闲置书籍</p>
      </div>

      <form @submit.prevent="handleSubmit" class="space-y-8">
        <div class="card p-8">
          <h2 class="text-xl font-semibold text-gray-900 dark:text-white mb-6">基本信息</h2>
          
          <div class="space-y-6">
            <div>
              <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                书籍封面 <span class="text-red-500">*</span>
              </label>
              <div class="flex items-center space-x-4">
                <div
                  class="w-32 h-40 bg-gray-100 dark:bg-gray-700 rounded-lg flex items-center justify-center border-2 border-dashed border-gray-300 dark:border-gray-600 cursor-pointer hover:border-primary-500 transition-colors"
                  @click="triggerFileInput"
                >
                  <img v-if="form.cover" :src="form.cover" class="w-full h-full object-cover rounded-lg">
                  <div v-else class="text-center">
                    <svg class="w-8 h-8 text-gray-400 mx-auto mb-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 16l4.586-4.586a2 2 0 012.828 0L16 16m-2-2l1.586-1.586a2 2 0 012.828 0L20 14m-6-6h.01M6 20h12a2 2 0 002-2V6a2 2 0 00-2-2H6a2 2 0 00-2 2v12a2 2 0 002 2z"></path>
                    </svg>
                    <span class="text-sm text-gray-500">点击上传</span>
                  </div>
                </div>
                <div class="flex-1">
                  <p class="text-sm text-gray-600 dark:text-gray-400 mb-2">支持 JPG、PNG 格式，建议尺寸 600x800</p>
                  <button type="button" class="btn btn-secondary text-sm" @click="triggerFileInput">
                    选择图片
                  </button>
                  <input
                    ref="fileInput"
                    type="file"
                    accept="image/*"
                    class="hidden"
                    @change="handleFileChange"
                  >
                </div>
              </div>
            </div>

            <div>
              <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                书名 <span class="text-red-500">*</span>
              </label>
              <input
                v-model="form.title"
                type="text"
                placeholder="请输入书名"
                class="input"
                required
              >
            </div>

            <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
              <div>
                <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                  作者 <span class="text-red-500">*</span>
                </label>
                <input
                  v-model="form.author"
                  type="text"
                  placeholder="请输入作者"
                  class="input"
                  required
                >
              </div>
              <div>
                <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                  出版社
                </label>
                <input
                  v-model="form.publisher"
                  type="text"
                  placeholder="请输入出版社"
                  class="input"
                >
              </div>
            </div>

            <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
              <div>
                <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                  ISBN
                </label>
                <input
                  v-model="form.isbn"
                  type="text"
                  placeholder="请输入ISBN（选填）"
                  class="input"
                >
              </div>
              <div>
                <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                  分类 <span class="text-red-500">*</span>
                </label>
                <select v-model="form.category" class="input" required>
                  <option value="">请选择分类</option>
                  <option v-for="cat in categories" :key="cat" :value="cat">{{ cat }}</option>
                </select>
              </div>
            </div>

            <div>
              <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                书籍描述
              </label>
              <textarea
                v-model="form.description"
                rows="4"
                placeholder="请描述书籍的详细信息、使用情况等"
                class="input"
              ></textarea>
            </div>
          </div>
        </div>

        <div class="card p-8">
          <h2 class="text-xl font-semibold text-gray-900 dark:text-white mb-6">价格与成色</h2>
          
          <div class="space-y-6">
            <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
              <div>
                <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                  原价（元） <span class="text-red-500">*</span>
                </label>
                <input
                  v-model.number="form.originalPrice"
                  type="number"
                  placeholder="请输入原价"
                  class="input"
                  min="0"
                  step="0.01"
                  required
                >
              </div>
              <div>
                <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                  售价（元） <span class="text-red-500">*</span>
                </label>
                <input
                  v-model.number="form.price"
                  type="number"
                  placeholder="请输入售价"
                  class="input"
                  min="0"
                  step="0.01"
                  required
                >
              </div>
            </div>
            <p v-if="form.price && form.originalPrice" class="text-sm text-gray-600 dark:text-gray-400">
              折扣：{{ Math.round((1 - form.price / form.originalPrice) * 100) }}% · 省 ¥{{ (form.originalPrice - form.price).toFixed(2) }}
            </p>

            <div>
              <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                成色 <span class="text-red-500">*</span>
              </label>
              <div class="grid grid-cols-2 md:grid-cols-4 gap-4">
                <label
                  v-for="condition in conditions"
                  :key="condition.value"
                  :class="[
                    'card p-4 cursor-pointer transition-all',
                    form.condition === condition.value
                      ? 'border-primary-500 ring-2 ring-primary-500'
                      : 'hover:border-primary-300'
                  ]"
                >
                  <input
                    v-model="form.condition"
                    :value="condition.value"
                    type="radio"
                    class="hidden"
                  >
                  <div class="text-center">
                    <div class="text-2xl mb-2">{{ condition.icon }}</div>
                    <div class="font-medium text-gray-900 dark:text-white">{{ condition.label }}</div>
                    <div class="text-xs text-gray-600 dark:text-gray-400 mt-1">{{ condition.desc }}</div>
                  </div>
                </label>
              </div>
            </div>
          </div>
        </div>

        <div class="card p-8">
          <h2 class="text-xl font-semibold text-gray-900 dark:text-white mb-6">联系方式</h2>
          
          <div class="space-y-6">
            <div>
              <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                交易方式 <span class="text-red-500">*</span>
              </label>
              <div class="flex flex-wrap gap-4">
                <label
                  v-for="method in tradeMethods"
                  :key="method.value"
                  :class="[
                    'card px-4 py-3 cursor-pointer transition-all',
                    form.tradeMethod === method.value
                      ? 'border-primary-500 ring-2 ring-primary-500'
                      : 'hover:border-primary-300'
                  ]"
                >
                  <input
                    v-model="form.tradeMethod"
                    :value="method.value"
                    type="radio"
                    class="hidden"
                  >
                  <div class="flex items-center space-x-2">
                    <span class="text-xl">{{ method.icon }}</span>
                    <span class="font-medium text-gray-900 dark:text-white">{{ method.label }}</span>
                  </div>
                </label>
              </div>
            </div>

            <div>
              <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                交易地点
              </label>
              <input
                v-model="form.location"
                type="text"
                placeholder="请输入交易地点（如：图书馆、食堂等）"
                class="input"
              >
            </div>

            <div>
              <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                联系方式 <span class="text-red-500">*</span>
              </label>
              <input
                v-model="form.contact"
                type="text"
                placeholder="请输入微信号或手机号"
                class="input"
                required
              >
            </div>
          </div>
        </div>

        <div class="flex space-x-4">
          <button type="submit" class="flex-1 btn btn-primary text-lg py-3">
            立即发布
          </button>
          <button type="button" class="btn btn-secondary text-lg px-8" @click="handleCancel">
            取消
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const fileInput = ref(null)

const form = ref({
  cover: '',
  title: '',
  author: '',
  publisher: '',
  isbn: '',
  category: '',
  description: '',
  originalPrice: '',
  price: '',
  condition: '',
  tradeMethod: 'offline',
  location: '',
  contact: ''
})

const categories = ['教材教辅', '文学小说', '计算机', '经管', '外语', '其他']

const conditions = [
  { value: '全新', label: '全新', icon: '✨', desc: '未使用过' },
  { value: '九成新', label: '九成新', icon: '📚', desc: '轻微使用痕迹' },
  { value: '八成新', label: '八成新', icon: '📖', desc: '正常使用痕迹' },
  { value: '七成新', label: '七成新', icon: '📕', desc: '明显使用痕迹' }
]

const tradeMethods = [
  { value: 'offline', label: '线下交易', icon: '📍' },
  { value: 'online', label: '邮寄', icon: '📦' },
  { value: 'both', label: '均可', icon: '🔄' }
]

const triggerFileInput = () => {
  fileInput.value?.click()
}

const handleFileChange = (event) => {
  const file = event.target.files[0]
  if (file) {
    const reader = new FileReader()
    reader.onload = (e) => {
      form.value.cover = e.target.result
    }
    reader.readAsDataURL(file)
  }
}

const handleSubmit = () => {
  alert('书籍发布成功！')
  router.push('/books')
}

const handleCancel = () => {
  router.back()
}
</script>
