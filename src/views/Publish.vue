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
                <select v-model="form.categoryId" class="input" required>
                  <option value="">请选择分类</option>
                  <option v-for="cat in categories" :key="cat.value" :value="cat.value">{{ cat.label }}</option>
                </select>
              </div>
            </div>

            <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
              <div>
                <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                  出版时间
                </label>
                <input
                  v-model="form.publishDate"
                  type="month"
                  class="input"
                >
              </div>
              <div>
                <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                  页数
                </label>
                <input
                  v-model.number="form.pages"
                  type="number"
                  placeholder="请输入页数"
                  class="input"
                >
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

            <div>
              <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                书籍图片（选填）
              </label>
              <div class="flex items-center space-x-4">
                <input
                  ref="additionalImagesInput"
                  type="file"
                  accept="image/*"
                  multiple
                  class="hidden"
                  @change="handleAdditionalImagesChange"
                >
                <button 
                  type="button" 
                  class="btn btn-secondary text-sm" 
                  @click="additionalImagesInput?.click()"
                >
                  选择图片
                </button>
                <p class="text-sm text-gray-600 dark:text-gray-400">可上传多张图片展示书籍内页</p>
              </div>
              
              <div v-if="imagePreviews.length > 0" class="mt-4 grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 gap-4">
                <div 
                  v-for="(preview, index) in imagePreviews" 
                  :key="index"
                  class="relative group"
                >
                  <img 
                    :src="preview" 
                    class="w-full h-24 object-cover rounded-lg border border-gray-200 dark:border-gray-700"
                  >
                  <button
                    type="button"
                    @click="removeImage(index)"
                    class="absolute -top-2 -right-2 w-6 h-6 bg-red-500 text-white rounded-full flex items-center justify-center opacity-0 group-hover:opacity-100 transition-opacity"
                  >
                    <svg class="w-3 h-3" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path>
                    </svg>
                  </button>
                </div>
              </div>
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

            <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
              <div>
                <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                  库存数量 <span class="text-red-500">*</span>
                </label>
                <input
                  v-model.number="form.stock"
                  type="number"
                  min="1"
                  placeholder="请输入库存数量"
                  class="input"
                  required
                >
              </div>
              <div>
                <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                  成色 <span class="text-red-500">*</span>
                </label>
                <select v-model="form.condition" class="input" required>
                  <option value="">请选择成色</option>
                  <option v-for="condition in conditions" :key="condition.value" :value="condition.value">
                    {{ condition.label }}
                  </option>
                </select>
              </div>
            </div>
          </div>
        </div>

        <div class="flex space-x-4">
          <button type="submit" class="flex-1 btn btn-primary text-lg py-3" :disabled="submitting">
            {{ submitting ? '发布中...' : '立即发布' }}
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
import { uploadFile, createBook } from '@/api/index'

const router = useRouter()

const fileInput = ref(null)
const additionalImagesInput = ref(null)
const imagePreviews = ref([])
const submitting = ref(false)

const form = ref({
  cover: '',
  title: '',
  author: '',
  publisher: '',
  publishDate: '',
  pages: null,
  categoryId: '',
  condition: '九成新',
  price: '',
  originalPrice: '',
  stock: 1,
  images: [],
  description: ''
})

const categories = [
  { value: 'textbook', label: '教材教辅' },
  { value: 'novel', label: '文学小说' },
  { value: 'computer', label: '计算机' },
  { value: 'management', label: '经管' },
  { value: 'language', label: '外语' },
  { value: 'other', label: '其他' }
]

const conditions = [
  { value: '全新', label: '全新' },
  { value: '九成新', label: '九成新' },
  { value: '八成新', label: '八成新' },
  { value: '七成新', label: '七成新' },
  { value: '六成新', label: '六成新' }
]

const triggerFileInput = () => {
  fileInput.value?.click()
}

const handleFileChange = async (event) => {
  const file = event.target.files[0]
  if (!file) {
    return
  }

  try {
    submitting.value = true
    const response = await uploadFile(file, 'cover')
    form.value.cover = response.data
  } catch (error) {
    console.error('封面上传失败:', error)
    alert('封面上传失败，请重试')
  } finally {
    submitting.value = false
  }
}

const handleAdditionalImagesChange = async (event) => {
  const files = Array.from(event.target.files)
  if (files.length === 0) {
    return
  }

  try {
    submitting.value = true
    for (const file of files) {
      const response = await uploadFile(file, 'image')
      form.value.images.push(response.data)
      imagePreviews.value.push(response.data)
    }
  } catch (error) {
    console.error('图片上传失败:', error)
    alert('图片上传失败，请重试')
  } finally {
    submitting.value = false
  }
}

const removeImage = (index) => {
  form.value.images.splice(index, 1)
  imagePreviews.value.splice(index, 1)
}

const handleSubmit = async () => {
  if (!form.value.title || !form.value.author || !form.value.categoryId || 
      !form.value.condition || !form.value.price || !form.value.originalPrice || 
      !form.value.cover) {
    alert('请填写所有必填项')
    return
  }

  try {
    submitting.value = true
    const bookData = {
      title: form.value.title,
      author: form.value.author,
      isbn: form.value.isbn || undefined,
      publisher: form.value.publisher || undefined,
      publishDate: form.value.publishDate || undefined,
      pages: form.value.pages || undefined,
      categoryId: form.value.categoryId,
      condition: form.value.condition,
      price: parseFloat(form.value.price),
      originalPrice: parseFloat(form.value.originalPrice),
      stock: parseInt(form.value.stock) ||1,
      cover: form.value.cover,
      images: form.value.images,
      description: form.value.description || undefined
    }

    const response = await createBook(bookData)
    
    if (response.code === 200) {
      alert('书籍发布成功！')
      router.push('/books')
    } else {
      alert(response.message || '发布失败')
    }
  } catch (error) {
    console.error('发布书籍失败:', error)
    alert(error.response?.data?.message || '发布失败，请重试')
  } finally {
    submitting.value = false
  }
}

const handleCancel = () => {
  router.back()
}
</script>
