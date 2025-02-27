package com.example.demo.repositories;

import com.example.demo.entities.Book;
import com.example.demo.entities.DownloadRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DownloadRequestRepository extends JpaRepository<DownloadRequest, Long> {
}
