package com.example.dictionary.service.impl;

import com.example.dictionary.access.minio.MinioServiceProperties;
import com.example.dictionary.access.minio.MinioServiceTemplate;
import com.example.dictionary.common.utils.AssertUtils;
import com.example.dictionary.model.dto.Base64FileDto;
import com.example.dictionary.service.ToolService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

import static com.example.dictionary.common.constant.StringConstant.*;
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
    private MinioServiceTemplate minioServiceTemplate;
    private MinioServiceProperties minioServiceProperties;

    @Override
    public List<String> getUuid(int num) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i< num; i++)
            list.add(UUID.randomUUID().toString().replaceAll(CONNECTOR,BLANK));
        return list;
    }

    @Override
    public String upload(MultipartFile file, String bucketName) throws Exception{
        InputStream inputStream = file.getInputStream();
        String contentType = file.getContentType();
        String fileName = file.getOriginalFilename();
        AssertUtils.isNotNull(fileName, FILE_NAME_EXIST_FAIL);
        String objectName = getObjectName(fileName);
        minioServiceTemplate.putObject(bucketName, objectName, inputStream, contentType);
        return String.format(minioServiceProperties.getUrl(), bucketName, objectName);
    }

    @Override
    public String upload64(Base64FileDto base64File, String bucketName) throws Exception{
        byte[] decode = Base64.getDecoder().decode(base64File.getBody());
        ByteArrayInputStream inputStream = new ByteArrayInputStream(decode);
        String contentType = base64File.getContentType();
        String objectName = getObjectName(base64File.getName());
        minioServiceTemplate.putObject(bucketName, objectName, inputStream, contentType);
        return String.format(minioServiceProperties.getUrl(), bucketName, objectName);
    }

    @Override
    public Optional<InputStream> download(String path) throws Exception{
        String[] strings = path.split(SLASH);
        String bucketName = strings[6];
        String objectName = strings[7];
        return minioServiceTemplate.getObject(bucketName, objectName);
    }

    private String getObjectName(String fileName){
        String suffix = fileName.replaceAll(P_FILE_NAME, BLANK);
        String uuid = UUID.randomUUID().toString().replace(CONNECTOR, BLANK);
        return uuid+DOT+suffix;
    }
}
