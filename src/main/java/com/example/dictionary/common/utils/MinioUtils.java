package com.example.dictionary.common.utils;

import io.minio.MinioClient;
import lombok.AllArgsConstructor;

import java.io.InputStream;
import java.util.Optional;

/**
 * minio封装
 *
 * @author DongerKai
 * @since 2019/6/11 8:40 ，1.0
 **/
@AllArgsConstructor
public class MinioUtils {
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
}
