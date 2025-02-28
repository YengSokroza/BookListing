# BookListing

BookListing is a Vue.js-based web application that allows users to explore and subscribe to a diverse collection of free books, including both fiction and non-fiction genres. The app provides a clean and simple interface for browsing, reading, and downloading books.

## Features

- **Explore Books**: Browse a wide variety of books, from fiction to non-fiction, all available for free.
- **Subscription**: Users can subscribe by entering their email to receive updates about new book additions and other library-related news.
- **Responsive Design**: Fully responsive layout optimized for desktop, tablet, and mobile devices.

## Project Structure

The project is divided into two main sections:

### 1. **BookListing-UI (Frontend)**

This section is responsible for the user interface built using Vue.js. It handles rendering the book listing, subscription form, and user interaction.

### **+ Frontend Routes**
The following routes are available in the frontend:
- `/` (Home)
 Displays the homepage with a list of books, a subscription form, and introductory content.
- `/books/{id}`
 Displays a detailed view of a single book (based on its ID).




### 2. **BookListing-Backend**

The backend is responsible for handling API requests, managing book data, and handling subscriptions. It is built with Spring Boot.

### **+ Backend Endpoints**
- `GET /api/books`
 Fetch a list of all books available.
- `POST /api/subscribe`
 Subscribe a user by email.
- `GET /api/book/{id}`
 Get details about a specific book by its ID.
- `POST /api/book`
 Add a new book.
- `PUT /api/book/{id}`
 Update a book's information by ID
- `DELETE /api/book/{id}`
 Delete a book from the library by ID
 - `POST /api/book/download`
 Download a book by ID through email

Here are the available endpoints in the backend:


## Installation

### Prerequisites

Before you begin, ensure you have the following installed:

- **Node.js** (LTS version recommended)
- **Vue.js CLI** (if you want to develop locally)
- **Docker** (for containerized deployment)
- **Java 17+** (for the backend)

### Clone the repository

```bash
git clone https://github.com/YengSokroza/BookListing.git
```
For Frontend:

```bash
npm install
npm run dev
```

## Docker Deployment

This app uses multiple environment files for configuration. You will need to create the following files in the root of your project:

- `.env.dev`
- `.env.prod`

Make sure you add the necessary environment variables for development (`.env.dev`) and production (`.env.prod`).

### For Backend
To start the application with the production profile, use the following command:

```bash
$env:SPRING_PROFILES_ACTIVE="prod"; docker-compose --profile prod up
```


