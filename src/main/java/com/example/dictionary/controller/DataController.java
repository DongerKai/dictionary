package com.example.dictionary.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "数据处理")
@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/data")
public class DataController {

    @ApiOperation("数据处理")
    @GetMapping("/test")
    public String test(){
        return "SUCCESS";
    }


}
