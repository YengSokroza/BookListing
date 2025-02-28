<script setup>
import { Mail } from 'lucide-vue-next';
import { watch, ref, onMounted } from 'vue';
import { Dialog, DialogPanel, DialogTitle, TransitionChild, TransitionRoot } from '@headlessui/vue'
import BookCardSingle from '@/components/Card/BookCardSingle.vue';
import { ArrowLeft } from 'lucide-vue-next';
import { useRoute, useRouter } from 'vue-router';

const route = useRoute();
const router = useRouter();
const books = ref([]);
const open = ref(false);
const selectedBook = ref(null);
const isExpanded = ref(false);
const email = ref('');

const book = ref(null);
const bookId = route.params.id;
console.log("id:", bookId)

const fetchBooks = async () => {
    try {
        const apiUrl = import.meta.env.VITE_APP_API_URL;
        const res = await fetch(`${apiUrl}book/${bookId}`);
        const data = await res.json();
        book.value = data;
        console.log("books:", book)
    } catch (error) {
        console.error('Error fetching books:', error);
    }
};


const fetchAllBooks = async () => {
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
}

const goHome = () => {
    router.push('/');
};

const openBookModal = (book) => {
    selectedBook.value = book.id;
    open.value = true;
};

const toggleDescription = () => {
    isExpanded.value = !isExpanded.value;
};

const subscribe = () => {
    alert(`Subscription email: ${email.value}`);
};

const isModalOpen = ref(false);



const openBookDetail = (book) => {
    selectedBook.value = book.id;
    isModalOpen.value = true;
    router.push({ name: 'BookDetail', params: { id: book.id } });
};

const loading = ref(false);

const download = async () => {
    const apiUrl = import.meta.env.VITE_APP_API_URL;

    // Ensure email is not empty
    if (!email.value) {
        message.value = "Please enter your email.";
        return;
    }

    if(!bookId){
        message.value = "Please enter your email.";
        return;
    }

    try {
        loading.value = true; // Start the loading state

        const response = await fetch(`${apiUrl}book/download`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ bookId: bookId , email: email.value }) // Ensure correct body format
        });

        // Check if response is JSON before parsing
        const contentType = response.headers.get("content-type");
        let result;

        if (contentType && contentType.includes("application/json")) {
            result = await response.json();
        } else {
            result = await response.text(); // Handle non-JSON response
        }

        console.log("Subscription Response:", result);

        if (response.ok) {
            console.log(typeof result === "string" ? result : "Send to email successfully!");
        } else {
            console.log(typeof result === "string" ? result : (result.message || "Send to email failed."));
        }
    } catch (error) {
        console.error('Error subscribing:', error);
        message.value = "An error occurred.";
    } finally {
        loading.value = false; // End the loading state
        email.value = "";
    }
};

onMounted(() => {
    fetchBooks();
    fetchAllBooks();
});

</script>

<template>
    <div v-if="book">
        <div class="!mx-auto max-w-7xl p-4 xl:p-6 ">
            <div>
                <button @click="goHome" class="pt-4 pb-6 text-lg truncate max-w-xs hover:cursor-pointer">
                    <ArrowLeft size="24" class="inline " /> {{ book.title }}
                </button>
            </div>
            <div class="container flex !gap-8 flex-wrap pb-6">
                <div class="book-container">
                    <!-- Book Image -->
                    <img :src="book.coverLink" alt="book" loading="lazy" class="book-image" />
                </div>

                <div class="book-info !space-y-2 md:!space-y-4 text-wrap">
                    <h1 class="text-2xl md:text-4xl !font-semibold">{{ book.title }}</h1>
                    <p class="text-green-600 text-lg md:text-xl italic">{{ book.author }}</p>

                    <div class="text-lg md:text-xl text-wrap">
                        <!-- Truncated Description -->
                        <p v-if="isExpanded" class="expanded-description">{{ book.description }}</p>
                        <p v-else class="description-line-clamp">{{ book.description }}</p>

                        <!-- Toggle button -->
                        <button @click="toggleDescription" class="text-green-600 text-md md:text-lg">
                            {{ isExpanded ? 'Read Less' : 'Read More' }}
                        </button>

                    </div>


                    <div>
                        <button @click="open = true"
                            class="w-full md:w-auto bg-[#bae27a] text-slate-800 !font-semibold px-6 py-3 rounded-md hover:bg-[#bae271] transition  ">Send
                            to
                            <Mail size="18px" class="inline !ml-1" />
                        </button>
                    </div>



                </div>
            </div>
        </div>


        <!-- Book Listing -->
        <div class="bg-slate-50 ">
            <div class="max-w-7xl py-2 md:py-6 px-2 md:px-4 !mx-auto">
                <p class="!font-semibold text-2xl lg:text-3xl text-gray-800 py-4 xl:py-6">You may interested in</p>
                <div class="book-list grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5 gap-1">
                    <BookCardSingle v-for="book in books" :key="book.id" :book-id="book.id" :bookImage="book.coverLink
                        " @click="openBookDetail(book)" />
                </div>


            </div>
        </div>

        <!-- Modal -->
        <div>
            <TransitionRoot as="template" :show="open">
                <Dialog class="relative z-10" @close="open = false">
                    <TransitionChild as="template" enter="ease-out duration-300" enter-from="opacity-0"
                        enter-to="opacity-100" leave="ease-in duration-200" leave-from="opacity-100"
                        leave-to="opacity-0">
                        <div class="fixed inset-0 bg-gray-500/75 transition-opacity" />
                    </TransitionChild>

                    <div class="fixed inset-0 z-10 w-screen overflow-y-auto">
                        <div class="flex min-h-full items-end justify-center p-4 text-center sm:items-center sm:p-0">
                            <TransitionChild as="template" enter="ease-out duration-300"
                                enter-from="opacity-0 translate-y-4 sm:translate-y-0 sm:scale-95"
                                enter-to="opacity-100 translate-y-0 sm:scale-100" leave="ease-in duration-200"
                                leave-from="opacity-100 translate-y-0 sm:scale-100"
                                leave-to="opacity-0 translate-y-4 sm:translate-y-0 sm:scale-95">
                                <DialogPanel
                                    class="relative transform overflow-hidden rounded-lg bg-white text-left shadow-xl transition-all sm:my-8 sm:w-full sm:max-w-lg">
                                    <div class="bg-white px-4 pt-5 pb-4 sm:p-6 sm:pb-4">
                                        <div>
                                            <div class="text-4xl sm:mt-0 sm:!ml-4 text-center !mb-2">🎉</div>
                                            <div class="mt-3 text-center sm:mt-0 sm:!ml-4 sm:text-left">
                                                <DialogTitle as="h3" class="text-xl !font-semibold text-gray-900">
                                                    Get Your Free Download Link!
                                                </DialogTitle>
                                                <div class="mt-2 !space-y-4 pb-4">
                                                    <p class="text-base text-gray-500 ">Enter your email address below,
                                                        and we'll send you a link to download the book for free. It's
                                                        easy, and completely free!</p>
                                                    <form @submit.prevent="download" class="w-full ">
                                                        <div class="flex flex-col items-center gap-2">
                                                            <input v-model="email" type="email"
                                                                placeholder="Enter your email" required
                                                                class="w-full flex-grow p-2 border border-gray-300 rounded-md text-sm focus:outline-none focus:ring-2 focus:ring-[#bae27a]" />
                                                            <div class="w-full !space-y-2">
                                                                <button type="submit" :disabled="loading"
                                                                    class="w-full bg-[#bae27a] text-slate-800 !font-semibold px-6 py-2 rounded-md hover:bg-[#bae271] transition cursor-pointer disabled:bg-gray-400 disabled:cursor-not-allowed">
                                                                    <span v-if="loading">Sending...</span>
                                                                    <span v-else>Send</span>
                                                                </button>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                </DialogPanel>
                            </TransitionChild>
                        </div>
                    </div>
                </Dialog>
            </TransitionRoot>
        </div>

    </div>

    <div v-else class="min-h-screen">
        <p>Loading book details...</p>
    </div>


</template>




<style scoped>
.book-container {
    width: 250px;
    /* Set exact width */
    height: 350px;
    /* Set exact height */
    position: relative;
    flex-shrink: 0;
    box-shadow: rgba(0, 0, 0, 0.15) 0px 1.1px 1.5px,
        rgba(0, 0, 0, 0.1) 0px 2.8px 3.9px, rgba(0, 0, 0, 0.08) 0px 5.8px 7.9px,
        rgba(0, 0, 0, 0.06) 0px 12.0455px 16.4px, rgba(0, 0, 0, 0.04) 0px 33px 45px;
    border-radius: 3px;
    overflow: hidden;
    display: flex;
    justify-content: center;
    align-items: center;
}

.book-container img {
    width: 100%;
    /* Make the image fit the container */
    height: 100%;
    object-fit: cover;
    /* Ensures the image maintains its aspect ratio */
    border-radius: 3px;
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

.book-info {
    min-width: 300px;
    min-height: 350px;
}

.container {
    display: flex;
    gap: 1rem;
    align-items: flex-start;
    flex-wrap: wrap;

}

@media (min-width: 768px) {
    .flex {
        flex-wrap: nowrap;
        /* Prevent wrapping */
    }

    .book-container {
        flex: 0 0 250px;
        /* Fixed size */
    }

    .book-info {
        flex: 1;
        /* Take remaining space */
        max-width: 100%;
        /* Limit width */
    }
}

/* Center on smaller screens */
@media (max-width: 767px) {
    .flex {
        flex-direction: column;
        align-items: center;
        text-align: center;
    }

    .book-info {
        max-width: 100%;
    }
}

.book-info .description-line-clamp {
    display: -webkit-box;
    -webkit-line-clamp: 6;
    /* Limit to 6 lines */
    -webkit-box-orient: vertical;
    overflow: hidden;
    text-overflow: ellipsis;
}

.book-info .expanded-description {
    max-height: none;
    /* Remove height restriction */
    overflow: visible;
    /* Allow content to show */
}
</style>
