<script setup>
import { RouterLink, RouterView } from 'vue-router'
import IndexNavbar from './components/Common/IndexNavbar.vue';
import IndexFooterVue from './components/Common/IndexFooter.vue'

</script>

<template>

  <IndexNavbar/>

  <RouterView :key="$route.params.id" />

<IndexFooterVue/>

</template>

<style scoped>

</style>
