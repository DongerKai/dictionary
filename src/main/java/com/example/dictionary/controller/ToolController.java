package com.example.dictionary.controller;

import com.example.dictionary.common.model.ApiResult;
import com.example.dictionary.service.ToolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

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

    @ApiOperation("文件上传")
    @PostMapping("/upload")
    public ApiResult upload(@ApiParam(name = "file", value = "上传的文件", required = true)
                                @NotEmpty(message = "文件不能为空！") @RequestParam(value = "file")MultipartFile file,
                            @ApiParam(name = "bucketName", value = "桶名称", required = true)
                            @NotBlank(message = "桶名称不能为空") @RequestParam(value = "bucketName") String bucketName)throws Exception{
        return format(SUCCESS, toolService.upload(file, bucketName));
    }
}
