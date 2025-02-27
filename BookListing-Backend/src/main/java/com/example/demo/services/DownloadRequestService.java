package com.example.demo.services;

import com.example.demo.entities.DownloadRequest;
import com.example.demo.repositories.DownloadRequestRepository;
import org.springframework.stereotype.Service;

@Service
public class DownloadRequestService {

    private final DownloadRequestRepository downloadRequestRepository;

    public DownloadRequestService(DownloadRequestRepository downloadRequestRepository) {
        this.downloadRequestRepository = downloadRequestRepository;
    }

    public DownloadRequest save(DownloadRequest downloadRequest) {
        return downloadRequestRepository.save(downloadRequest);
    }
}
