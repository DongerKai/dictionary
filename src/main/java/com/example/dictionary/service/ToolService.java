package com.example.dictionary.service;

import org.springframework.web.multipart.MultipartFile;

public interface ToolService {
    String upload(MultipartFile file, String bucketName) throws Exception;
}
