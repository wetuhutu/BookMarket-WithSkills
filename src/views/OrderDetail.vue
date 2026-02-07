<template>
  <div class="min-h-screen py-12">
    <div class="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8">
      <div v-if="loading" class="flex justify-center items-center py-20">
        <div class="animate-spin rounded-full h-12 w-12 border-b-2 border-primary-600"></div>
      </div>

      <div v-else-if="error" class="text-center py-12">
        <div class="text-6xl mb-4">❌</div>
        <p class="text-gray-600 dark:text-gray-400 mb-4">{{ error }}</p>
        <button @click="fetchOrderDetail" class="btn btn-primary">重试</button>
      </div>

      <div v-else-if="order" class="space-y-6">
        <div class="flex items-center justify-between">
          <h1 class="text-2xl font-bold text-gray-900 dark:text-white">订单详情</h1>
          <router-link to="/profile" class="text-primary-600 dark:text-primary-400 hover:underline">
            返回我的订单
          </router-link>
        </div>

        <div class="card p-6">
          <div class="flex items-start justify-between mb-6">
            <div>
              <div class="text-sm text-gray-600 dark:text-gray-400 mb-1">订单号</div>
              <div class="text-lg font-semibold text-gray-900 dark:text-white">{{ order.orderNo }}</div>
            </div>
            <span :class="[
              'px-4 py-2 rounded-full text-sm font-medium',
              getOrderStatusClass(order.status)
            ]">
              {{ getOrderStatusText(order.status) }}
            </span>
          </div>

          <div class="grid grid-cols-2 md:grid-cols-4 gap-4">
            <div>
              <div class="text-sm text-gray-600 dark:text-gray-400 mb-1">下单时间</div>
              <div class="font-medium text-gray-900 dark:text-white">{{ formatDateTime(order.createdAt) }}</div>
            </div>
            <div v-if="order.paidAt">
              <div class="text-sm text-gray-600 dark:text-gray-400 mb-1">付款时间</div>
              <div class="font-medium text-gray-900 dark:text-white">{{ formatDateTime(order.paidAt) }}</div>
            </div>
            <div v-if="order.shippedAt">
              <div class="text-sm text-gray-600 dark:text-gray-400 mb-1">发货时间</div>
              <div class="font-medium text-gray-900 dark:text-white">{{ formatDateTime(order.shippedAt) }}</div>
            </div>
            <div v-if="order.receivedAt">
              <div class="text-sm text-gray-600 dark:text-gray-400 mb-1">收货时间</div>
              <div class="font-medium text-gray-900 dark:text-white">{{ formatDateTime(order.receivedAt) }}</div>
            </div>
          </div>
        </div>

        <div class="card p-6">
          <h2 class="text-lg font-semibold text-gray-900 dark:text-white mb-4">商品信息</h2>
          <div class="flex items-start space-x-6">
            <img :src="order.bookCover" :alt="order.bookTitle" class="w-32 h-40 object-cover rounded-lg">
            <div class="flex-1">
              <h3 class="text-xl font-semibold text-gray-900 dark:text-white mb-2">{{ order.bookTitle }}</h3>
              <div class="space-y-2 text-sm text-gray-600 dark:text-gray-400">
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
          <h2 class="text-lg font-semibold text-gray-900 dark:text-white mb-4">卖家信息</h2>
          <div class="flex items-center space-x-4">
            <div class="w-12 h-12 bg-primary-100 dark:bg-primary-900 rounded-full flex items-center justify-center">
              <span class="text-xl">👨‍🎓</span>
            </div>
            <div class="flex-1">
              <div class="font-semibold text-gray-900 dark:text-white">{{ order.sellerName }}</div>
              <div class="text-sm text-gray-600 dark:text-gray-400">卖家ID：{{ order.sellerId }}</div>
            </div>
            <button class="btn btn-secondary text-sm">联系卖家</button>
          </div>
        </div>

        <div v-if="order.status === 'pending'" class="card p-6 bg-yellow-50 dark:bg-yellow-900/20">
          <div class="flex items-center justify-between">
            <div>
              <h3 class="font-semibold text-gray-900 dark:text-white mb-1">等待付款</h3>
              <p class="text-sm text-gray-600 dark:text-gray-400">请在24小时内完成付款，否则订单将自动取消</p>
            </div>
            <div class="flex items-center space-x-3">
              <button @click="handleCancelOrder" class="btn btn-secondary text-sm">
                取消订单
              </button>
              <button @click="goToPayment" class="btn btn-primary text-sm">
                立即支付
              </button>
            </div>
          </div>
        </div>

        <div v-if="order.status === 'shipped'" class="card p-6 bg-purple-50 dark:bg-purple-900/20">
          <div class="flex items-center justify-between">
            <div>
              <h3 class="font-semibold text-gray-900 dark:text-white mb-1">订单已发货</h3>
              <p class="text-sm text-gray-600 dark:text-gray-400">请确认收到的商品无误后，点击确认收货</p>
            </div>
            <button @click="handleConfirmOrder" class="btn btn-primary text-sm">
              确认收货
            </button>
          </div>
        </div>

        <div v-if="order.status === 'received'" class="card p-6 bg-green-50 dark:bg-green-900/20">
          <div class="flex items-center space-x-3">
            <svg class="w-8 h-8 text-green-500" fill="currentColor" viewBox="0 0 20 20">
              <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clip-rule="evenodd"></path>
            </svg>
            <div>
              <h3 class="font-semibold text-gray-900 dark:text-white">订单已完成</h3>
              <p class="text-sm text-gray-600 dark:text-gray-400">感谢您的购买！</p>
            </div>
          </div>
        </div>

        <div v-if="order.status === 'cancelled'" class="card p-6 bg-gray-50 dark:bg-gray-800">
          <div class="flex items-center space-x-3">
            <svg class="w-8 h-8 text-gray-400" fill="currentColor" viewBox="0 0 20 20">
              <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zM8.707 7.293a1 1 0 00-1.414 1.414L8.586 10l-1.293 1.293a1 1 0 101.414 1.414L10 11.414l1.293 1.293a1 1 0 001.414-1.414L11.414 10l1.293-1.293a1 1 0 00-1.414-1.414L10 8.586 8.707 7.293z" clip-rule="evenodd"></path>
            </svg>
            <div>
              <h3 class="font-semibold text-gray-900 dark:text-white">订单已取消</h3>
              <p class="text-sm text-gray-600 dark:text-gray-400">该订单已被取消</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getOrderDetail, cancelOrder, confirmOrder } from '@/api/index'

const route = useRoute()
const router = useRouter()

const loading = ref(true)
const error = ref(null)
const order = ref(null)

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

const getOrderStatusClass = (status) => {
  const classes = {
    'pending': 'bg-yellow-100 text-yellow-600 dark:bg-yellow-900 dark:text-yellow-400',
    'paid': 'bg-blue-100 text-blue-600 dark:bg-blue-900 dark:text-blue-400',
    'shipped': 'bg-purple-100 text-purple-600 dark:bg-purple-900 dark:text-purple-400',
    'received': 'bg-green-100 text-green-600 dark:bg-green-900 dark:text-green-400',
    'cancelled': 'bg-gray-100 text-gray-600 dark:bg-gray-700 dark:text-gray-400'
  }
  return classes[status] || 'bg-gray-100 text-gray-600 dark:bg-gray-700 dark:text-gray-400'
}

const getOrderStatusText = (status) => {
  const statusMap = {
    'pending': '待付款',
    'paid': '待发货',
    'shipped': '已发货',
    'received': '已收货',
    'cancelled': '已取消'
  }
  return statusMap[status] || status
}

const formatDateTime = (dateStr) => {
  if (!dateStr) return '-'
  const date = new Date(dateStr)
  return date.toLocaleString('zh-CN', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit'
  })
}

const goToPayment = () => {
  router.push(`/payment/${order.value.id}`)
}

const handleCancelOrder = async () => {
  const reason = prompt('请输入取消订单的原因（可选）')
  if (reason === null) {
    return
  }

  try {
    const response = await cancelOrder(order.value.id, reason)
    if (response.code === 200) {
      alert('订单已取消')
      await fetchOrderDetail()
    } else {
      alert(response.message || '取消订单失败')
    }
  } catch (error) {
    console.error('取消订单失败:', error)
    alert(error.response?.data?.message || '取消订单失败，请稍后重试')
  }
}

const handleConfirmOrder = async () => {
  if (!confirm('确认收货后，订单将完成。确定要确认收货吗？')) {
    return
  }

  try {
    const response = await confirmOrder(order.value.id)
    if (response.code === 200) {
      alert('确认收货成功')
      await fetchOrderDetail()
    } else {
      alert(response.message || '确认收货失败')
    }
  } catch (error) {
    console.error('确认收货失败:', error)
    alert(error.response?.data?.message || '确认收货失败，请稍后重试')
  }
}

onMounted(() => {
  fetchOrderDetail()
})
</script>