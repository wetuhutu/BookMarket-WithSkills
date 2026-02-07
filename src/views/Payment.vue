<template>
  <div class="min-h-screen py-12 bg-gray-50 dark:bg-gray-900">
    <div class="max-w-2xl mx-auto px-4 sm:px-6 lg:px-8">
      <div v-if="loading" class="flex justify-center items-center py-20">
        <div class="animate-spin rounded-full h-12 w-12 border-b-2 border-primary-600"></div>
      </div>

      <div v-else-if="error" class="text-center py-12">
        <div class="text-6xl mb-4">❌</div>
        <p class="text-gray-600 dark:text-gray-400 mb-4">{{ error }}</p>
        <button @click="fetchOrderDetail" class="btn btn-primary">重试</button>
      </div>

      <div v-else-if="order" class="space-y-6">
        <div class="flex items-center justify-between mb-6">
          <h1 class="text-2xl font-bold text-gray-900 dark:text-white">支付订单</h1>
          <router-link :to="`/orders/${order.id}`" class="text-primary-600 dark:text-primary-400 hover:underline">
            返回订单详情
          </router-link>
        </div>

        <div class="card p-6">
          <h2 class="text-lg font-semibold text-gray-900 dark:text-white mb-4">订单信息</h2>
          <div class="flex items-start space-x-6">
            <img :src="order.bookCover" :alt="order.bookTitle" class="w-32 h-40 object-cover rounded-lg">
            <div class="flex-1">
              <h3 class="text-xl font-semibold text-gray-900 dark:text-white mb-2">{{ order.bookTitle }}</h3>
              <div class="space-y-2 text-sm text-gray-600 dark:text-gray-400">
                <div>订单号：{{ order.orderNo }}</div>
                <div>数量：{{ order.quantity }}</div>
                <div>单价：¥{{ order.price }}</div>
                <div class="text-lg font-bold text-primary-600 dark:text-primary-400">
                  总价：¥{{ order.totalPrice }}
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="card p-6">
          <h2 class="text-lg font-semibold text-gray-900 dark:text-white mb-4">支付方式</h2>
          <div class="space-y-4">
            <label v-for="method in paymentMethods" :key="method.id" 
              :class="[
                'flex items-center p-4 border-2 rounded-lg cursor-pointer transition-all',
                selectedPaymentMethod === method.id
                  ? 'border-primary-600 bg-primary-50 dark:bg-primary-900/20'
                  : 'border-gray-200 dark:border-gray-700 hover:border-gray-300 dark:hover:border-gray-600'
              ]"
              @click="selectedPaymentMethod = method.id"
            >
              <input type="radio" :value="method.id" v-model="selectedPaymentMethod" class="hidden">
              <div class="flex items-center space-x-4">
                <div class="w-12 h-12 bg-white dark:bg-gray-800 rounded-lg flex items-center justify-center text-2xl">
                  {{ method.icon }}
                </div>
                <div class="flex-1">
                  <div class="font-semibold text-gray-900 dark:text-white">{{ method.name }}</div>
                  <div class="text-sm text-gray-600 dark:text-gray-400">{{ method.description }}</div>
                </div>
                <div v-if="selectedPaymentMethod === method.id" class="w-6 h-6 bg-primary-600 rounded-full flex items-center justify-center">
                  <svg class="w-4 h-4 text-white" fill="currentColor" viewBox="0 0 20 20">
                    <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clip-rule="evenodd"></path>
                  </svg>
                </div>
              </div>
            </label>
          </div>
        </div>

        <div class="card p-6">
          <h2 class="text-lg font-semibold text-gray-900 dark:text-white mb-4">支付金额</h2>
          <div class="text-center py-6">
            <div class="text-4xl font-bold text-gray-900 dark:text-white mb-2">
              ¥{{ order.totalPrice }}
            </div>
            <div class="text-sm text-gray-600 dark:text-gray-400">
              请确认支付金额无误
            </div>
          </div>
        </div>

        <div class="card p-6 bg-blue-50 dark:bg-blue-900/20">
          <h2 class="text-lg font-semibold text-gray-900 dark:text-white mb-4">安全提示</h2>
          <ul class="space-y-2 text-sm text-gray-600 dark:text-gray-400">
            <li class="flex items-start">
              <svg class="w-5 h-5 text-blue-500 mr-2 flex-shrink-0" fill="currentColor" viewBox="0 0 20 20">
                <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clip-rule="evenodd"></path>
              </svg>
              请确保在安全的网络环境下进行支付
            </li>
            <li class="flex items-start">
              <svg class="w-5 h-5 text-blue-500 mr-2 flex-shrink-0" fill="currentColor" viewBox="0 0 20 20">
                <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clip-rule="evenodd"></path>
              </svg>
              支付完成后请勿关闭浏览器，等待跳转
            </li>
            <li class="flex items-start">
              <svg class="w-5 h-5 text-blue-500 mr-2 flex-shrink-0" fill="currentColor" viewBox="0 0 20 20">
                <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clip-rule="evenodd"></path>
              </svg>
              如遇支付问题，请联系客服
            </li>
          </ul>
        </div>

        <div class="flex items-center space-x-4">
          <button @click="handleCancel" class="flex-1 btn btn-secondary text-lg py-3">
            取消支付
          </button>
          <button 
            @click="handlePay" 
            :disabled="paying"
            class="flex-1 btn btn-primary text-lg py-3"
          >
            {{ paying ? '支付中...' : '确认支付' }}
          </button>
        </div>
      </div>

      <div v-if="showSuccessModal" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
        <div class="card p-8 max-w-md mx-4 text-center">
          <div class="w-20 h-20 bg-green-100 dark:bg-green-900 rounded-full flex items-center justify-center mx-auto mb-4">
            <svg class="w-10 h-10 text-green-500" fill="currentColor" viewBox="0 0 20 20">
              <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clip-rule="evenodd"></path>
            </svg>
          </div>
          <h3 class="text-2xl font-bold text-gray-900 dark:text-white mb-2">支付成功！</h3>
          <p class="text-gray-600 dark:text-gray-400 mb-6">您的订单已支付成功，我们将尽快为您发货</p>
          <button @click="goToOrderDetail" class="btn btn-primary w-full">
            查看订单详情
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()

const loading = ref(true)
const error = ref(null)
const order = ref(null)
const paying = ref(false)
const showSuccessModal = ref(false)
const selectedPaymentMethod = ref('wechat')

const paymentMethods = [
  {
    id: 'wechat',
    name: '微信支付',
    description: '推荐使用微信支付',
    icon: '💚'
  },
  {
    id: 'alipay',
    name: '支付宝',
    description: '安全便捷的支付方式',
    icon: '💙'
  },
  {
    id: 'bank',
    name: '银行卡支付',
    description: '支持各大银行卡',
    icon: '💳'
  }
]

const fetchOrderDetail = async () => {
  try {
    loading.value = true
    error.value = null

    const response = await fetch('/api/users/orders', {
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      }
    })

    if (response.ok) {
      const data = await response.json()
      if (data.code === 200 && data.data) {
        const orders = data.data.list || []
        const foundOrder = orders.find(o => o.id === parseInt(route.params.id))
        if (foundOrder) {
          order.value = foundOrder
        } else {
          error.value = '订单不存在'
        }
      } else {
        error.value = data.message || '获取订单列表失败'
      }
    } else {
      error.value = '获取订单列表失败'
    }
  } catch (err) {
    console.error('获取订单详情失败:', err)
    error.value = '获取订单详情失败，请稍后重试'
  } finally {
    loading.value = false
  }
}

const handleCancel = () => {
  if (confirm('确定要取消支付吗？')) {
    router.push(`/orders/${route.params.id}`)
  }
}

const handlePay = async () => {
  if (!selectedPaymentMethod.value) {
    alert('请选择支付方式')
    return
  }

  try {
    paying.value = true
    
    await new Promise(resolve => setTimeout(resolve, 2000))
    
    showSuccessModal.value = true
  } catch (error) {
    console.error('支付失败:', error)
    alert('支付失败，请稍后重试')
  } finally {
    paying.value = false
  }
}

const goToOrderDetail = () => {
  router.push(`/orders/${route.params.id}`)
}

onMounted(() => {
  fetchOrderDetail()
})
</script>