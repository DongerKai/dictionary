package com.example.dictionary.controller;

import com.example.dictionary.common.model.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.dictionary.common.model.ApiResult.State.SUCCESS;
import static com.example.dictionary.common.model.ApiResult.format;

@Api(tags = "数据处理")
@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/data")
public class DataController {

    @ApiOperation("数据处理")
    @GetMapping("/test")
    public ApiResult test(){
        return format(SUCCESS);
    }


}
