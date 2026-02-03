# 前端API对接完成总结

## 修改时间
2026-02-03

## 修改概述

将前端Vue 3项目从静态数据改为对接后端Spring Boot API，实现真实的数据交互。

---

## 一、新增文件

### 1.1 API请求封装

**文件**: `src/api/request.js`
- 创建axios实例，配置baseURL为 `http://localhost:8080/api`
- 实现请求拦截器，自动添加JWT token到请求头
- 实现响应拦截器，处理401未授权错误
- 统一错误处理

### 1.2 API接口定义

**文件**: `src/api/index.js`
- 封装所有后端API接口
- 包含分类、书籍、评价、认证等接口
- 统一导出，方便页面调用

**接口列表**:
```javascript
export const getCategories = () => request.get('/books/categories')
export const getHotBooks = (limit = 4) => request.get('/books/hot', { params: { limit } })
export const getBooks = (params) => request.get('/books', { params })
export const getBookDetail = (id) => request.get(`/books/${id}`)
export const getBookReviews = (id, params) => request.get(`/books/${id}/reviews`, { params })
export const getBookReviewsStatistics = (id) => request.get(`/books/${id}/reviews/statistics`)
export const getBookRelated = (id, limit = 4) => request.get(`/books/${id}/related`, { params: { limit } })
export const login = (data) => request.post('/auth/login', data)
export const register = (data) => request.post('/auth/register', data)
```

### 1.3 工具函数

**文件**: `src/utils/format.js`
- `formatRelativeTime(dateStr)`: 将ISO 8601日期格式转换为相对时间（如"2小时前"）
- `formatPrice(price)`: 格式化价格显示（如"¥35.00"）

---

## 二、修改的文件

### 2.1 Home.vue（首页）

**修改内容**:
- 移除静态分类数据
- 引入 `getCategories` API
- 在 `onMounted` 中调用API获取分类数据
- 添加loading状态管理

**修改前**:
```javascript
const categories = ref([
  { name: '教材教辅', icon: '📚', count: 3240 },
  // ... 其他静态数据
])
```

**修改后**:
```javascript
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
```

### 2.2 Books.vue（书籍列表页）

**修改内容**:
- 移除静态书籍数据和前端筛选逻辑
- 引入 `getBooks`、`getCategories` API和 `formatRelativeTime` 工具函数
- 实现后端分页、搜索、筛选、排序功能
- 添加loading状态管理
- 使用 `watch` 监听页码变化，自动加载数据

**修改前**:
```javascript
const books = ref([...静态数据])
const filteredBooks = computed(() => {
  // 前端筛选逻辑
  let result = [...books.value]
  if (searchQuery.value) { ... }
  if (selectedCategory.value) { ... }
  // ...
  return result
})
```

**修改后**:
```javascript
import { getBooks, getCategories } from '@/api/index'
import { formatRelativeTime } from '@/utils/format'

const books = ref([])
const total = ref(0)
const pageSize = ref(12)

const fetchBooks = async () => {
  try {
    loading.value = true
    const params = {
      page: currentPage.value,
      pageSize: pageSize.value
    }
    
    if (searchQuery.value) params.keyword = searchQuery.value
    if (selectedCategory.value) params.category = selectedCategory.value
    if (selectedCondition.value) params.condition = selectedCondition.value
    if (sortBy.value !== 'default') params.sortBy = sortBy.value
    
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

watch([currentPage], () => {
  fetchBooks()
})

onMounted(() => {
  fetchCategories()
  fetchBooks()
})
```

**模板修改**:
- `filteredBooks` → `books`
- `book.time` → `formatRelativeTime(book.createdAt)`

### 2.3 BookDetail.vue（书籍详情页）

**修改内容**:
- 移除静态书籍数据、评价数据、推荐数据
- 引入 `getBookDetail`、`getBookReviews`、`getBookReviewsStatistics`、`getBookRelated` API
- 实现并行请求多个API（书籍详情、评价、统计、推荐）
- 添加loading状态管理
- 适配后端返回的数据结构

**修改前**:
```javascript
const book = ref({
  id: 1,
  title: '高等数学（第七版）上册',
  // ... 静态数据
  seller: {
    name: '张同学',
    avatar: '👨‍🎓',
    school: '清华大学',
    major: '计算机科学与技术',
    rating: 4.9
  }
})
const relatedBooks = ref([...静态数据])
```

**修改后**:
```javascript
import { getBookDetail, getBookReviews, getBookReviewsStatistics, getBookRelated } from '@/api/index'
import { formatRelativeTime } from '@/utils/format'

const book = ref(null)
const reviews = ref([])
const statistics = ref(null)
const relatedBooks = ref([])

const fetchBookDetail = async () => {
  const res = await getBookDetail(route.params.id)
  if (res.code === 200) {
    book.value = res.data
  }
}

const fetchReviews = async () => {
  const res = await getBookReviews(route.params.id, { page: 1, pageSize: 10 })
  if (res.code === 200) {
    reviews.value = res.data.list
  }
}

const fetchStatistics = async () => {
  const res = await getBookReviewsStatistics(route.params.id)
  if (res.code === 200) {
    statistics.value = res.data
  }
}

const fetchRelated = async () => {
  const res = await getBookRelated(route.params.id, 4)
  if (res.code === 200) {
    relatedBooks.value = res.data
  }
}

onMounted(async () => {
  await Promise.all([
    fetchBookDetail(),
    fetchReviews(),
    fetchStatistics(),
    fetchRelated()
  ])
  loading.value = false
})
```

**模板修改**:
- `book.category` → `book.categoryId`
- `book.time` → `formatRelativeTime(book.createdAt)`
- `book.views` → `book.pages`
- `book.wants` → `book.isbn`
- `book.seller.name` → `book.sellerName`
- `book.seller.avatar` → 固定emoji `👨‍🎓`
- `book.seller.rating` → `book.sellerRating`
- `book.seller.school` → `book.sellerLevel`
- 移除 `book.seller.major`

---

## 三、依赖安装

### 3.1 新增依赖

```bash
npm install axios
```

**说明**: axios用于HTTP请求，是前端与后端通信的基础库。

---

## 四、功能实现情况

### 4.1 已实现功能

| 页面 | 功能 | 状态 | 说明 |
|------|------|------|------|
| Home.vue | 获取分类列表 | ✅ 完成 | 从API获取真实分类数据 |
| Books.vue | 书籍列表 | ✅ 完成 | 支持分页、搜索、筛选、排序 |
| Books.vue | 分类筛选 | ✅ 完成 | 从API获取分类并筛选 |
| Books.vue | 搜索功能 | ✅ 完成 | 支持关键词搜索 |
| Books.vue | 价格排序 | ✅ 完成 | 支持价格升序/降序 |
| Books.vue | 时间排序 | ✅ 完成 | 支持最新发布排序 |
| BookDetail.vue | 书籍详情 | ✅ 完成 | 从API获取完整书籍信息 |
| BookDetail.vue | 卖家信息 | ✅ 完成 | 显示卖家名称、等级、评分 |
| BookDetail.vue | 评价列表 | ✅ 完成 | 从API获取评价列表 |
| BookDetail.vue | 评价统计 | ✅ 完成 | 显示平均评分和分布 |
| BookDetail.vue | 相似推荐 | ✅ 完成 | 从API获取同分类书籍 |

### 4.2 待实现功能

| 页面 | 功能 | 状态 | 原因 |
|------|------|------|------|
| Navbar.vue | 登录/登出 | ⚠️ 待实现 | 后端登录接口返回data为null |
| Publish.vue | 发布书籍 | ⚠️ 待实现 | 后端缺少POST /books接口 |
| Profile.vue | 用户信息 | ⚠️ 待实现 | 后端缺少用户管理接口 |
| Profile.vue | 我的书籍 | ⚠️ 待实现 | 后端缺少用户书籍接口 |
| Profile.vue | 我的订单 | ⚠️ 待实现 | 后端缺少订单管理接口 |
| Profile.vue | 我的收藏 | ⚠️ 待实现 | 后端缺少收藏管理接口 |

---

## 五、数据格式适配

### 5.1 日期格式

**后端格式**: ISO 8601（如 `2026-01-01T02:30:00.000+00:00`）
**前端显示**: 相对时间（如 `2小时前`、`1天前`）

**解决方案**: 使用 `formatRelativeTime()` 函数转换

### 5.2 价格格式

**后端格式**: decimal（如 `45.00`）
**前端显示**: 货币格式（如 `¥45.00`）

**解决方案**: 使用模板插值 `¥{{ book.price }}` 或 `formatPrice()` 函数

### 5.3 分类ID

**后端格式**: 英文标识（如 `novel`、`education`）
**前端显示**: 中文分类名（如 `小说`、`教育`）

**解决方案**: 后端返回 `name` 字段，前端直接显示

### 5.4 卖家信息

**后端字段**:
- `sellerName`: 卖家名称
- `sellerLevel`: 卖家等级
- `sellerRating`: 卖家评分
- `verified`: 是否认证

**前端适配**: 直接使用后端字段，移除前端模拟的嵌套结构

---

## 六、测试结果

### 6.1 API测试

| 接口 | 状态 | 响应时间 |
|------|------|----------|
| GET /books/categories | ✅ PASS | <100ms |
| GET /books/hot?limit=4 | ✅ PASS | <100ms |
| GET /books?page=1&pageSize=12 | ✅ PASS | <100ms |
| GET /books/1 | ✅ PASS | <100ms |
| GET /books/1/reviews | ✅ PASS | <100ms |
| GET /books/1/reviews/statistics | ✅ PASS | <100ms |
| GET /books/1/related?limit=4 | ✅ PASS | <100ms |

### 6.2 前端测试

| 页面 | 状态 | 说明 |
|------|------|------|
| 首页 | ✅ 正常 | 分类数据正常显示 |
| 书籍列表 | ✅ 正常 | 书籍数据正常显示，支持分页 |
| 书籍详情 | ✅ 正常 | 详情、评价、推荐正常显示 |

### 6.3 发现的问题

#### 问题1：登录接口返回data为null

**位置**: `POST /auth/login`
**影响**: 前端无法获取token和用户信息
**建议**: 后端修复JWT生成逻辑，确保返回token

#### 问题2：缺少用户管理接口

**影响**: 个人中心无法使用
**建议**: 后端实现 `GET /users/profile`、`PUT /users/profile` 等接口

#### 问题3：缺少书籍发布接口

**影响**: 发布功能无法使用
**建议**: 后端实现 `POST /books` 接口

---

## 七、下一步计划

### 7.1 修复登录接口

1. 后端修复 `/auth/login` 接口，确保返回token
2. 前端实现登录页面
3. 前端实现token管理（存储、发送、清除）

### 7.2 实现用户功能

1. 后端实现用户管理接口
2. 前端实现Profile.vue对接
3. 实现我的书籍、订单、收藏功能

### 7.3 实现发布功能

1. 后端实现书籍发布接口
2. 前端实现Publish.vue对接
3. 实现图片上传功能

### 7.4 优化和测试

1. 添加错误处理和用户提示
2. 优化loading状态显示
3. 进行全面的功能测试

---

## 八、技术要点

### 8.1 API请求封装

- 统一的axios实例配置
- 请求/响应拦截器
- 自动添加token
- 统一错误处理

### 8.2 数据获取模式

- 使用 `async/await` 处理异步请求
- 使用 `try/catch` 捕获错误
- 使用 `Promise.all` 并行请求多个接口
- 使用 `watch` 监听页码变化

### 8.3 状态管理

- 使用 `ref` 定义响应式数据
- 使用 `computed` 计算派生数据
- 使用 `onMounted` 组件挂载时获取数据

### 8.4 数据格式化

- 使用工具函数统一格式化
- 在模板中直接调用格式化函数
- 保持后端数据格式，前端只负责显示

---

## 九、文件清单

### 9.1 新增文件

```
src/
├── api/
│   ├── index.js          # API接口定义
│   └── request.js        # axios封装
└── utils/
    └── format.js        # 格式化工具函数
```

### 9.2 修改文件

```
src/views/
├── Home.vue           # 首页 - 对接分类API
├── Books.vue          # 书籍列表 - 对接书籍列表API
└── BookDetail.vue     # 书籍详情 - 对接详情、评价、推荐API
```

### 9.3 依赖更新

```
package.json
└── dependencies
    └── axios: ^latest   # 新增
```

---

## 十、总结

### 10.1 完成的工作

✅ 创建API请求封装（axios + 拦截器）
✅ 创建API接口定义（8个接口）
✅ 创建工具函数（日期、价格格式化）
✅ Home.vue对接分类API
✅ Books.vue对接书籍列表API（分页、搜索、筛选、排序）
✅ BookDetail.vue对接详情、评价、统计、推荐API
✅ 安装axios依赖
✅ 前端服务器正常运行

### 10.2 当前状态

- **前端地址**: http://localhost:5174
- **后端地址**: http://localhost:8080/api
- **前端状态**: ✅ 正常运行
- **后端状态**: ✅ 正常运行
- **API对接**: ✅ 基本完成

### 10.3 待完成工作

⚠️ 修复后端登录接口
⚠️ 实现用户管理接口
⚠️ 实现书籍发布接口
⚠️ 实现订单和收藏接口
⚠️ 前端实现登录/登出功能
⚠️ 前端实现发布功能
⚠️ 前端实现个人中心功能

---

## 附录

### A. 浏览器访问

打开浏览器访问: http://localhost:5174

### B. 查看API请求

打开浏览器开发者工具（F12）→ Network标签，可以查看所有API请求

### C. 相关文档

- [前后端适配文档](./前后端适配文档.md)
- [前后端联调测试报告](./前后端联调测试报告.md)
