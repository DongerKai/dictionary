package com.example.dictionary.access.minio;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @author DongerKai
 * @since 2019/6/12 13:09 ，1.0
 **/
@Data
@Component
@ConfigurationProperties(prefix = "minio")
@Configuration
public class MinioServiceProperties {
    private String endpoint;
    private Key key;
    private String url;

    @SuppressWarnings("WeakerAccess")
    @Data
    public static class Key{
        private String access;
        private String secret;
    }
    @Bean
    public MinioClient minioClient()throws Exception{
        return new MinioClient(endpoint, key.getAccess(), key.getSecret());
    }
}
