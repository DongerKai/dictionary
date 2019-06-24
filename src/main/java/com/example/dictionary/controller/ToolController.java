package com.example.dictionary.controller;

import com.example.dictionary.common.model.ApiResult;
import com.example.dictionary.common.utils.IoUtils;
import com.example.dictionary.model.dataobject.BookDo;
import com.example.dictionary.model.dataobject.UserDo;
import com.example.dictionary.model.dto.Base64FileDto;
import com.example.dictionary.service.ToolService;
import com.example.dictionary.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;

import java.awt.print.Book;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.example.dictionary.common.constant.StringConstant.*;
import static com.example.dictionary.common.model.ApiResult.STATE.SUCCESS;
import static com.example.dictionary.common.model.ApiResult.format;
import static com.example.dictionary.configuration.RabbitMQConfiguration.DELAY_ROUTING_KEY;
import static com.example.dictionary.configuration.RabbitMQConfiguration.REGISTER_DELAY_EXCHANGE;

/**
 * 工具合集 controller
 *
 * @author DongerKai
 * @since 2019/6/10 16:38 ，1.0
 **/
@Api(tags = "工具合集")
@AllArgsConstructor
@RestController
@RequestMapping("/tool")
@Validated
@Slf4j
public class ToolController {
    private ToolService toolService;
    private final RabbitTemplate rabbitTemplate;
    private UserService userService;

    @ApiOperation("获取随机UUID无横线")
    @GetMapping("/get-uuid")
    public ApiResult<List<String>> getUuid(@ApiParam(name = "num", value = "数目")
                                           @RequestParam(value = "num", required = false, defaultValue = "1") int num){
        return format(SUCCESS, toolService.getUuid(num));
    }

    @ApiOperation("文件上传")
    @PostMapping("/upload")
    public ApiResult upload(@ApiParam(name = "file", value = "上传的文件", required = true)
                                 @RequestParam(value = "file")MultipartFile file,
                            @ApiParam(name = "bucketName", value = "桶名称", required = true)
                            @NotBlank(message = "桶名称不能为空") @RequestParam(value = "bucketName") String bucketName)throws Exception{
        return format(SUCCESS, toolService.upload(file, bucketName));
    }

    @ApiOperation("base64文件上传")
    @PostMapping("/upload-64")
    public ApiResult upload64(@ApiParam(name = "base64File", value = "base64File", required = true)
                              @Validated @RequestBody Base64FileDto base64File,
                              @ApiParam(name = "bucketName",  value = "桶名称", required = true)
                              @RequestParam(value = "bucketName") String bucketName)throws Exception{
        return format(SUCCESS, toolService.upload64(base64File, bucketName));
    }

    @ApiOperation("文件下载")
    @GetMapping("/download/**")
    public void download(HttpServletRequest request, HttpServletResponse response)throws Exception{
        String path = URLDecoder.decode(request.getRequestURI(), StandardCharsets.UTF_8.toString());
        Optional<InputStream> inputStream = toolService.download(path);
        response.setCharacterEncoding(CHARSET_UTF_8);
        if (inputStream.isPresent()){
            InputStream in = inputStream.get();
            ServletOutputStream out = response.getOutputStream();
            IoUtils.pipe(in, out);
        }
    }

    @ApiOperation("发送消息")
    @GetMapping("/send")
    public void defaultMessage(){
        UserDo user = userService.selectById("b7dc3bd2f6d048fd98b2dd21ccb903bc");
        rabbitTemplate.convertAndSend(DEFAULT_BOOK_QUEUE, user);
        rabbitTemplate.convertAndSend(MANUAL_BOOK_QUEUE, user);
    }

    @ApiOperation("发送延迟消息")
    @GetMapping("/send/delay")
    public void delayMessage(){
        BookDo book = new BookDo();
        book.setId("1");
        book.setName("name");
        rabbitTemplate.convertAndSend(REGISTER_DELAY_EXCHANGE, DELAY_ROUTING_KEY, book, message -> {
            message.getMessageProperties().setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, Book.class.getName());
            message.getMessageProperties().setExpiration(5 * 1000 + "");
            return message;
        });
        log.info("发送时间：{}", LocalDateTime.now());
    }
}
