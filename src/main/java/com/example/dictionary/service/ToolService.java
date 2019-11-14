package com.example.dictionary.service;

import com.example.dictionary.model.dto.Base64FileDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public interface ToolService {
    String upload(MultipartFile file, String bucketName) throws Exception;

    String upload64(Base64FileDto base64File, String bucketName) throws Exception;

    List<String> getUuid(int num);

    Optional<InputStream> download(String path) throws Exception;

    String split(String text, String prefix);

    String replace(String text, String replace, String origin);
}
