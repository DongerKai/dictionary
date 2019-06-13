package com.example.dictionary.controller;

import com.example.dictionary.common.model.ApiResult;
import com.example.dictionary.common.utils.IoUtils;
import com.example.dictionary.model.dto.Base64FileDto;
import com.example.dictionary.service.ToolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;

import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import static com.example.dictionary.common.constant.StringConstant.*;
import static com.example.dictionary.common.model.ApiResult.STATE.SUCCESS;
import static com.example.dictionary.common.model.ApiResult.format;

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
public class ToolController {
    private ToolService toolService;

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
}
