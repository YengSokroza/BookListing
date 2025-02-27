import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import Main from '@/components/Main.vue'
import BookDetail from '@/views/books/BookDetail.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },

    {
      path: '/books/:id',
      name: 'BookDetail',
      component: BookDetail
    }

  ],
})

export default router
