package com.example.demo.controllers;

import com.example.demo.dto.BookDTO;
import com.example.demo.dto.DownloadRequestDTO;
import com.example.demo.dto.UpdateBookDTO;
import com.example.demo.entities.Book;
import com.example.demo.entities.DownloadRequest;
import com.example.demo.entities.Subscriber;
import com.example.demo.services.BookService;
import com.example.demo.services.DownloadRequestService;
import com.example.demo.services.EmailService;
import com.example.demo.services.SubscriberService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private DownloadRequestService downloadRequestService;

    @Autowired
    private SubscriberService subscriberService;

    @Autowired
    private EmailService emailService;

    // GET /books - Get a list of all books
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.findAll();
        return ResponseEntity.ok(books);
    }

    // GET /books/{id} - Get details of a specific book
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable UUID id) {
        return bookService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /books - Add a new book
//    @PostMapping
//    public ResponseEntity<Book> addBook(@RequestBody Book book) {
//        Book createdBook = bookService.save(book);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
//    }

    @PostMapping
    public ResponseEntity<Book> addBook(@Valid @RequestBody BookDTO bookDTO) {
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setDescription(bookDTO.getDescription());
        book.setDownloadLink(bookDTO.getDownloadLink());
        book.setCoverLink(bookDTO.getCoverLink());

        return ResponseEntity.ok(bookService.save(book));
    }

    // PUT /books/{id} - Update book details (Admin-only)
//    @PutMapping("/{id}")
//    public ResponseEntity<Book> updateBook(@PathVariable UUID id, @RequestBody Book book) {
//        return bookService.update(id, book)
//                .map(updatedBook -> ResponseEntity.ok(updatedBook))
//                .orElse(ResponseEntity.notFound().build());
//    }
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable UUID id, @RequestBody UpdateBookDTO updateBookDTO) {
        return bookService.findById(id)
                .map(existingBook -> {
                    if (updateBookDTO.getTitle() != null) {
                        existingBook.setTitle(updateBookDTO.getTitle());
                    }
                    if (updateBookDTO.getAuthor() != null) {
                        existingBook.setAuthor(updateBookDTO.getAuthor());
                    }
                    if (updateBookDTO.getDescription() != null) {
                        existingBook.setDescription(updateBookDTO.getDescription());
                    }
                    if (updateBookDTO.getDownloadLink() != null) {
                        existingBook.setDownloadLink(updateBookDTO.getDownloadLink());
                    }
                    if (updateBookDTO.getCoverLink() != null) {
                        existingBook.setCoverLink(updateBookDTO.getCoverLink());
                    }

                    Book updatedBook = bookService.save(existingBook);
                    return ResponseEntity.ok(updatedBook);
                })
                .orElse(ResponseEntity.notFound().build());
    }


    // DELETE /books/{id} - Delete a book (Admin-only)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable UUID id) {
        if (bookService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/download")
    public ResponseEntity<String> requestDownload(@RequestBody DownloadRequestDTO downloadRequestDTO) {
        // Retrieve the book from the database using the book ID

        UUID bookId;
        try {
            bookId = UUID.fromString(downloadRequestDTO.getBookId());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid book ID format.");
        }

        // Retrieve the book from the database using the UUID
        Optional<Book> bookOptional = bookService.findById(bookId);

        if (bookOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found.");
        }

        // Check if the email exists in the Subscriber table
        Optional<Subscriber> subscriberOptional = subscriberService.findByEmail(downloadRequestDTO.getEmail());

        // If the subscriber does not exist, add a new subscriber
        Subscriber subscriber;
        if (subscriberOptional.isEmpty()) {
            subscriber = new Subscriber();
            subscriber.setEmail(downloadRequestDTO.getEmail());
            subscriber = subscriberService.save(subscriber); // Save new subscriber
        } else {
            subscriber = subscriberOptional.get();
        }

        // Save the download request
        DownloadRequest downloadRequest = new DownloadRequest(subscriber, bookOptional.get());
        downloadRequestService.save(downloadRequest);


        // Send an email with the download link from the Book entity
//        String downloadLink = bookOptional.get().getDownloadLink();
//        emailService.sendDownloadLink(downloadRequestDTO.getEmail(), downloadLink);
        try {
            emailService.sendDownloadLink(downloadRequestDTO.getEmail(), bookOptional.get().getDownloadLink());
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email.");
        }


        return ResponseEntity.status(HttpStatus.OK).body("Download request received. Check your email for the download link.");
    }


}
