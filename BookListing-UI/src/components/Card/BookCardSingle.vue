<template>
    <router-link :to="`/books/${bookId}`" class="block">
        <div class="book-container">
            <!-- Book Image -->
            <img :src="bookImage" alt="book" loading="lazy" class="w-full h-auto rounded-lg" />
        </div>
    </router-link>
</template>

<script setup>

// Define props for the book details
const props = defineProps({
    bookId: {
        type: String,
        required: true
    },
    bookImage: {
        type: String,
        required: true
    },
});
</script>

<style scoped>
.item {
    max-width: 300px;
    margin: 0 auto;
    overflow: hidden;
    perspective: 1000px;
    /* Add perspective for the book-like effect */
}

.book-container {
    width: 100%;
    height: 100%;
    position: relative;
    box-shadow: rgba(0, 0, 0, 0.15) 0px 1.1px 1.5px,
        rgba(0, 0, 0, 0.1) 0px 2.8px 3.9px, rgba(0, 0, 0, 0.08) 0px 5.8px 7.9px,
        rgba(0, 0, 0, 0.06) 0px 12.0455px 16.4px, rgba(0, 0, 0, 0.04) 0px 33px 45px;
    border-radius: 3px;
    overflow: hidden;
}

.book-container img {
    border-radius: 3px;
    display: block;
    height: auto;
    object-fit: cover;
    width: 100%;
}

.book-container::before {
    content: "";
    position: absolute;
    inset: 0px;
    border-radius: 3px;
    pointer-events: none;
    filter: contrast(310%) brightness(100%);
    box-shadow: rgba(15, 15, 15, 0.1) 0px 0px 0px 1px inset;
    background: linear-gradient(90deg,
            rgba(0, 0, 0, 0.118) 0.65%,
            rgba(255, 255, 255, 0.2) 1.53%,
            rgba(255, 255, 255, 0.1) 2.38%,
            rgba(0, 0, 0, 0.05) 3.26%,
            rgba(255, 255, 255, 0.14) 5.68%,
            rgba(244, 244, 244, 0) 6.96%);
}
</style>