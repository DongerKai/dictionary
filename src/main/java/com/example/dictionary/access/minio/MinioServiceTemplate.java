package com.example.dictionary.access.minio;

import io.minio.MinioClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Optional;

/**
 * TODO
 *
 * @author DongerKai
 * @since 2019/6/12 13:09 ，1.0
 **/
@Component
@Slf4j
@AllArgsConstructor
public class MinioServiceTemplate {
    private MinioClient minioClient;

    public void putObject(String bucketName, String objectName, InputStream inputStream, String contentType)throws Exception{
        if (!minioClient.bucketExists(bucketName))
            minioClient.makeBucket(bucketName);
        minioClient.putObject(bucketName, objectName, inputStream, contentType);
    }

    public Optional<InputStream> getObject(String bucketName, String objectName)throws Exception{
        InputStream inputStream = minioClient.getObject(bucketName, objectName);
        return Optional.of(inputStream);
    }

    public boolean bucketExist(String bucketName) throws Exception{
        return minioClient.bucketExists(bucketName);
    }
}
