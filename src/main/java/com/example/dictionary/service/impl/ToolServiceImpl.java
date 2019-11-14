package com.example.dictionary.service.impl;

import com.example.dictionary.access.minio.MinioServiceProperties;
import com.example.dictionary.access.minio.MinioServiceTemplate;
import com.example.dictionary.common.utils.AssertUtils;
import com.example.dictionary.common.utils.CatchExceptionUtils;
import com.example.dictionary.model.dto.Base64FileDto;
import com.example.dictionary.service.ToolService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
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
@Slf4j
public class ToolServiceImpl implements ToolService {
    private MinioServiceTemplate minioServiceTemplate;
    private MinioServiceProperties minioServiceProperties;

    @Override
    public List<String> getUuid(int num) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i< num; i++)
            list.add(UUID.randomUUID().toString().replaceAll(CONNECTOR,BLANK));
        dateChange();
        return list;
    }

    private void dateChange(){
        Date today = new Date();//获取当前时间
        String todayStr = DateFormatUtils.format(today, DATE_FORMAT_02);//将Date日期转换为String，格式为"yyyy-HH-dd HH:mm:ss"
        Date todayDate = new Date();
        try {
            todayDate = DateUtils.parseDate(todayStr, DATE_FORMAT_01);//将格式为"yyyy-HH-dd HH:mm:ss"的String转为Date形式
        } catch (Exception e){
            log.error(e.getMessage());}
        Long endTimestamp = todayDate.getTime()/1000;
        Long startTimestamp = DateUtils.addMinutes(todayDate, -10).getTime()/1000;
        log.info("");
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
        String bucketName = strings[5];
        String objectName = strings[6];
        return minioServiceTemplate.getObject(bucketName, objectName);
    }

    @Override
    public String split(String text, String prefix) {
        String res="";
        for (String temp : text.split(", ")){
            res=res+prefix+temp+", ";
        }
        return res;
    }

    @Override
    public String replace(String text, String replace, String origin) {
        return text.replaceAll(origin,replace);
    }

    private String getObjectName(String fileName){
        String suffix = fileName.replaceAll(P_FILE_NAME, BLANK);
        String uuid = UUID.randomUUID().toString().replace(CONNECTOR, BLANK);
        return uuid+DOT+suffix;
    }
}
