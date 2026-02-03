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
