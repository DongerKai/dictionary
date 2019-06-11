package com.example.dictionary.service.impl;

import com.example.dictionary.common.utils.AssertUtils;
import com.example.dictionary.common.utils.MinioUtils;
import com.example.dictionary.service.ToolService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

import static com.example.dictionary.common.constant.StringConstant.State.FILE_NAME_EXIST_FAIL;

/**
 * 工具服务service
 *
 * @author DongerKai
 * @since 2019/6/10 16:43 ，1.0
 **/
@Service
@AllArgsConstructor
public class ToolServiceImpl implements ToolService {
    private MinioUtils minioUtils;

    @Override
    public String upload(MultipartFile file, String bucketName) throws Exception{
        InputStream inputStream = file.getInputStream();
        String contentType = file.getContentType();
        String fileName = file.getOriginalFilename();
        AssertUtils.isNotNull(fileName, FILE_NAME_EXIST_FAIL);
        String suffix = fileName.replaceAll(".*\\.", "");
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String objectName = uuid+"."+suffix;
        minioUtils.putObject(bucketName, objectName, inputStream, contentType);
        return String.format("/oss/%s/%s", bucketName, objectName);
    }
}
