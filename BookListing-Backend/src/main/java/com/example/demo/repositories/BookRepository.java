package com.example.demo.repositories;

import com.example.demo.entities.Book;
import com.example.demo.entities.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findById(UUID id);

    boolean existsById(UUID id);

    void deleteById(UUID id);
}
