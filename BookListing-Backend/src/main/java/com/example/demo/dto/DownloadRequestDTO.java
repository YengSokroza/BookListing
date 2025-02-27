package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class DownloadRequestDTO {
    private String email;
    private String bookId;

    public String getEmail() {
        return email;
    }

    public String getBookId() {
        return bookId;
    }
}