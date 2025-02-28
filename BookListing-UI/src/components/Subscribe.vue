<template>
    <div class="w-ful relative !min-h-auto">
        <div class="doodle-top-left"></div>
        <div class="doodle-bottom-right"></div>
        <div class="background-container bg-white">

            <div class="max-w-7xl py-6 md:py-10 px-4 md:px-6 text-center relative z-10">
                <!-- Text Section -->
                <div class="py-4">
                    <h1 class="!font-bold text-lg sm:text-xl md:text-2xl lg:text-4xl pb-4">
                        Join us today, explore and expand your knowledge all for free!
                    </h1>
                    <p class="text-sm sm:text-base lg:text-xl text-gray-800 pb-6">
                        Explore an extensive collection of books available to read, download, and enjoy completely for
                        free. Our library offers a diverse selection of genres, including fiction, non-fiction, and
                        more.
                    </p>
                </div>

                <!-- Subscription Form -->
                <div class="flex flex-col items-center w-full">
                    <form @submit.prevent="subscribe" class="w-full max-w-md">
                        <div class="flex flex-col sm:flex-row items-center gap-2">
                            <input v-model="email" type="email" placeholder="Enter your email" required
                                class="w-full sm:w-auto flex-grow p-3 border border-gray-300 rounded-md text-sm focus:outline-none focus:ring-2 focus:ring-[#bae27a]" />
                            <button type="submit"
                                class="w-full sm:w-auto bg-[#bae27a] text-slate-800 !font-semibold px-6 py-3 rounded-md hover:bg-[#bae271] transition">
                                Subscribe
                            </button>
                        </div>
                    </form>

                    <p v-if="message" class="message">{{ message }}</p>
                </div>
            </div>
        </div>
    </div>

</template>

<script setup>
import { ref } from 'vue';

const email = ref('');
const message = ref(''); // Define message

const subscribe = async () => {
    const apiUrl = import.meta.env.VITE_APP_API_URL;

    // Ensure email is not empty
    if (!email.value) {
        message.value = "Please enter your email.";
        return;
    }

    console.log(message.value)

    try {
        const response = await fetch(`${apiUrl}subscribe`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ email: email.value }) // Ensure correct body format
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
            showMessage(typeof result === "string" ? result : "Subscribed successfully!");
        } else {
            showMessage(typeof result === "string" ? result : (result.message || "Subscription failed."));
        }
    } catch (error) {
        console.error('Error subscribing:', error);
        message.value = "An error occurred.";
    } finally{
        email.value=""
    }
};

const showMessage = (msg) => {
    message.value = msg;
    setTimeout(() => {
        message.value = '';
    }, 3000); 
};
</script>

<style scoped>
/* Background and Layout */
.background-container {
    width: 100%;
    min-height: auto;
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    align-items: center;
    padding: 2rem;
}



/* Success/Error Messages */
.message {
    margin-top: 10px;
    color: green;
    font-size: 14px;
}

/* Doodles */
.doodle-top-left,
.doodle-bottom-right {
    position: absolute;
    background-size: contain;
    background-repeat: no-repeat;
}

.doodle-top-left {
    top: 10px;
    left: 10px;
    width: 15vw;
    height: 15vw;
    max-width: 200px;
    max-height: 200px;
    background-image: url('@/assets/doodle1.png');
}

.doodle-bottom-right {
    bottom: 10px;
    right: 10px;
    width: 15vw;
    height: 15vw;
    max-width: 200px;
    max-height: 200px;
    background-image: url('@/assets/doodle2.png');
}

/* Mobile Adjustments */
@media (max-width: 640px) {

    .doodle-top-left,
    .doodle-bottom-right {
        width: 20vw;
        height: 20vw;
        max-width: 120px;
        max-height: 120px;
    }
}
</style>
