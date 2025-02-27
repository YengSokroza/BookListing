<script setup>

import { ref, onMounted, computed } from 'vue';
import { Search } from 'lucide-vue-next';

import BookCardSingle from './Card/BookCardSingle.vue';
import Subscribe from './Subscribe.vue';


const searchQuery = ref('');

const handleSearch = () => {
    console.log('Searching for:', searchQuery.value);
    // Add search logic here
};

const filteredBooks = computed(() => {
    if (!searchQuery.value) {
        return books.value; // If no search query, return all books
    }
    const query = searchQuery.value.toLowerCase(); // Make search query case-insensitive
    return books.value.filter(book => {
        return (
            book.title.toLowerCase().includes(query) ||
            book.author.toLowerCase().includes(query)
        );
    });
});

const isModalOpen = ref(false);
const selectedBook = ref(null);

const openBookModal = (book) => {
    selectedBook.value = book.id;
    isModalOpen.value = true;
};

const closeModal = () => {
    isModalOpen.value = false;
    selectedBook.value = null;
};

// const books = [
//     {
//         id: 1,
//         url: "/book/28791747/8bc0d6/quicksilver-the-fae-alchemy-series-book-1.html?dsource=mostpopular",
//         image: "https://s3proxy.cdn-zlib.sk/covers300/collections/userbooks/c9c502d3044f58e3230939d3aa0f3d06cfbc1235c4e66115c9e29e14741f823d.jpg", // Replace with actual image source
//         title: "Quicksilver (The Fae & Alchemy Series Book 1)",
//         author: "Callie Hart",
//         description: "A captivating tale of magic, mystery, and the ever-present battle between light and dark...",
//         downloadLink: "/download-link"
//     },
//     {
//         id: 1,
//         url: "/book/28791747/8bc0d6/quicksilver-the-fae-alchemy-series-book-1.html?dsource=mostpopular",
//         image: "https://s3proxy.cdn-zlib.sk/covers300/collections/userbooks/c9c502d3044f58e3230939d3aa0f3d06cfbc1235c4e66115c9e29e14741f823d.jpg", // Replace with actual image source
//         title: "Quicksilver (The Fae & Alchemy Series Book 1)",
//         author: "Callie Hart",
//         description: "A captivating tale of magic, mystery, and the ever-present battle between light and dark...",
//         downloadLink: "/download-link"
//     },
//     {
//         id: 2,
//         url: "/book/28791747/8bc0d6/quicksilver-the-fae-alchemy-series-book-1.html?dsource=mostpopular",
//         image: "https://s3proxy.cdn-zlib.sk/covers300/collections/userbooks/c9c502d3044f58e3230939d3aa0f3d06cfbc1235c4e66115c9e29e14741f823d.jpg", // Replace with actual image source
//         title: "Quicksilver (The Fae & Alchemy Series Book 1)",
//         author: "Callie Hart",
//         description: "A captivating tale of magic, mystery, and the ever-present battle between light and dark...",
//         downloadLink: "/download-link"
//     },
//     {
//         id: 3,
//         url: "/book/28791747/8bc0d6/quicksilver-the-fae-alchemy-series-book-1.html?dsource=mostpopular",
//         image: "https://s3proxy.cdn-zlib.sk/covers300/collections/userbooks/c9c502d3044f58e3230939d3aa0f3d06cfbc1235c4e66115c9e29e14741f823d.jpg", // Replace with actual image source
//         title: "Quicksilver (The Fae & Alchemy Series Book 1)",
//         author: "Callie Hart",
//         description: "A captivating tale of magic, mystery, and the ever-present battle between light and dark...",
//         downloadLink: "/download-link"
//     },
//     {
//         id: 4,
//         url: "/book/28791747/8bc0d6/quicksilver-the-fae-alchemy-series-book-1.html?dsource=mostpopular",
//         image: "https://s3proxy.cdn-zlib.sk/covers300/collections/userbooks/c9c502d3044f58e3230939d3aa0f3d06cfbc1235c4e66115c9e29e14741f823d.jpg", // Replace with actual image source
//         title: "Quicksilver (The Fae & Alchemy Series Book 1)",
//         author: "Callie Hart",
//         description: "A captivating tale of magic, mystery, and the ever-present battle between light and dark...",
//         downloadLink: "/download-link"
//     },
//     // Add more books here...
// ];

// Define a reactive variable for books
const books = ref([]);

// Fetch data when the component is mounted
onMounted(() => {
    const apiUrl = import.meta.env.VITE_APP_API_URL;
    fetch(`${apiUrl}books`)
        .then(res => res.json())  // Make sure to call .json() here
        .then(data => {
            books.value = data;
            console.log('data:', data)  // Update the reactive `books` variable
        })
        .catch(err => {
            console.log('Error fetching books:', err);
        });
});

</script>

<template>

    <div class="flex flex-col min-h-screen">
        <main class="flex-grow">
            <div class="w-full flex flex-col justify-center">
                <!-- Header -->
                <div class="background-container ">

                    <div class="max-w-7xl  px-2 md:px-4  ">
                        <!-- Text Section -->
                        <div class="py-4 grid grid-cols-1 xl:grid-cols-2 gap-4">
                            <div class="col-span-1">
                                <h1 class="!font-bold text-2xl sm:text-3xl md:text-5xl xl:text-7xl p-2 xl:p-5">
                                    Expand Your Mind, One Free Book at a Time!
                                </h1>

                                <p class="text-[16px] sm:text-[18px] lg:text-2xl text-gray-800 p-2 xl:p-5">
                                    Explore an extensive collection of books available to read, download, and enjoy
                                    completely
                                    for
                                    free.
                                    Our library offers a diverse selection of genres, including fiction, non-fiction,
                                    and
                                    more.
                                </p>

                                <!-- Search Bar -->
                                <div class="w-full p-2 xl:p-4">
                                    <div class="w-full  relative">
                                        <input v-model="searchQuery" type="text" placeholder="Search for books..."
                                            class="bg-white w-full pl-4 pr-12 py-2 lg:py-3 border border-gray-300 rounded-[8px] focus:outline-none text-textprimary text-sm lg:text-base"
                                            @keydown.enter="handleSearch" />
                                        <div
                                            class="absolute right-2 top-1/2 transform -translate-y-1/2 bg-primary py-2 px-3 lg:py-3 lg:px-4 rounded-[8px] ">
                                            <Search color="#000000" size="18" stroke-width="2" />
                                        </div>
                                    </div>

                                </div>
                            </div>
                            <div class="col-span-1 hidden xl:block p-4">
                                <img src="/src/assets/books.png" alt="book" loading="lazy"
                                    class="w-full h-[500px] rounded-lg" />
                            </div>


                        </div>





                    </div>
                </div>
                <!-- Book Listing -->
                <div class="bg-slate-50">
                    <div class="max-w-7xl py-2 md:py-6 px-2 md:px-4 !mx-auto">
                        <p class="!font-bold text-[18px] sm:text-2xl lg:text-3xl text-gray-800 py-4 xl:py-6">Our
                            Collections</p>
                        <div v-if="filteredBooks.length > 0" class="book-list grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5 gap-1">
                            <BookCardSingle v-for="book in filteredBooks" :key="book.id" :book-id="book.id" :bookImage="book.coverLink
                                " @click="openBookModal(book)" />
                        </div>
                        <p v-else class="text-center text-xl text-gray-500 py-6">No books found</p>


                    </div>
                </div>


                <Subscribe class="flex-grow flex-wrap" />




            </div>
        </main>


    </div>


</template>




<style scoped>
.background-container {
    width: 100%;
    min-height: auto;
    /* position: relative; */
    display: flex;
    justify-content: center;
    align-items: center;
}

.doodle-top-left {
    position: absolute;
    top: 0;
    left: 0;
    width: 20vw;
    /* Makes the image size responsive */
    height: 20vw;
    /* Makes the image size responsive */
    max-width: 250px;
    /* Limit the size */
    max-height: 250px;
    /* Limit the size */
    background-image: url('src/assets/doodle1.png');
    background-size: contain;
    background-repeat: no-repeat;
}

.doodle-bottom-right {
    position: absolute;
    bottom: 0;
    right: 0;
    width: 20vw;
    /* Makes the image size responsive */
    height: 20vw;
    /* Makes the image size responsive */
    max-width: 250px;
    /* Limit the size */
    max-height: 250px;
    /* Limit the size */
    background-image: url('src/assets/doodle2.png');
    background-size: contain;
    background-repeat: no-repeat;
}



@media (max-width: 768px) {

    .doodle-top-left,
    .doodle-bottom-right {
        width: 30vw;
        /* Adjust the size on smaller screens */
        height: 30vw;
        /* Adjust the size on smaller screens */
        max-width: 200px;
        /* Adjust max-width */
        max-height: 200px;
        /* Adjust max-height */
    }
}
</style>