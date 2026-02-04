import request from './request'

export const getCategories = () => request.get('/books/categories')

export const getHotBooks = (limit = 4) => request.get('/books/hot', { params: { limit } })

export const getBooks = (params) => request.get('/books', { params })

export const getBookDetail = (id) => request.get(`/books/${id}`)

export const getBookReviews = (id, params) => request.get(`/books/${id}/reviews`, { params })

export const getBookReviewsStatistics = (id) => request.get(`/books/${id}/reviews/statistics`)

export const getBookRelated = (id, limit = 4) => request.get(`/books/${id}/related`, { params: { limit } })

export const login = (data) => request.post('/auth/login', data)

export const register = (data) => request.post('/auth/register', data)

export const getUserProfile = () => request.get('/users/profile')

export const updateUserProfile = (data) => request.put('/users/profile', data)

export const getMyBooks = (params) => request.get('/users/books', { params })

export const uploadFile = (file, type = 'image') => {
  const formData = new FormData()
  formData.append('file', file)
  formData.append('type', type)
  return request.post('/upload', formData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

export const getMyOrders = (params) => request.get('/users/orders', { params })

export const getFavorites = (params) => request.get('/users/favorites', { params })

export const addFavorite = (bookId) => request.post('/users/favorites', { bookId })

export const removeFavorite = (bookId) => request.delete(`/users/favorites/${bookId}`)
