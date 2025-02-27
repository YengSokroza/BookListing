<template>
  <nav
    class="top-0 sticky z-50 w-full flex items-center justify-center px-2  navbar-expand-lg bg-[#333333] shadow"
  >
    <div class="container px-4 mx-auto flex items-center justify-center">
      <div class="w-full relative flex justify-center lg:w-auto lg:static lg:block lg:justify-start">
        <!-- Logo -->
        <router-link to="/">
          <a
            class="text-blueGray-700 text-sm font-bold leading-relaxed inline-block mr-4  whitespace-nowrap uppercase"
          >
           
            <img src="@/assets/freebookherelogo.png" alt="Logo" class="h-18 xl:h-25" />
          </a>
        </router-link>
      </div>
    </div>
  </nav>
</template>

<script>
export default {
  data() {
    return {};
  },
};
</script>
