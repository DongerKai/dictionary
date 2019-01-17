package com.example.dictionary.controller;

import com.example.dictionary.base.model.PageInfo;
import com.example.dictionary.common.model.ApiResult;
import com.example.dictionary.model.dataObject.UserDo;
import com.example.dictionary.service.DataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;

import java.net.URLEncoder;

import static com.example.dictionary.common.constant.StringConstant.CHARSET_UTF_8;
import static com.example.dictionary.common.model.ApiResult.STATE.SUCCESS;
import static com.example.dictionary.common.model.ApiResult.format;

@Api(tags = "数据处理")
@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/data")
@Validated
public class DataController {

    private DataService dataService;

    @ApiOperation("数据处理")
    @GetMapping("/test")
    public String test(){
        return "success";
    }//一个最简单的mvc结构

    @ApiOperation("分页查询列表")
    @GetMapping("/user/qry-list")
    public ApiResult qryUserList(@ApiParam(name = "pageIndex", value = "当前页") @RequestParam(value = "pageIndex", required = false, defaultValue = "1") int pageIndex,
                             @ApiParam(name = "pageSize", value = "当前每页数量") @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize){
        PageInfo<UserDo> page = dataService.qryUserList(pageIndex, pageSize);
        return format(SUCCESS, page);
    }

    @ApiOperation("新增用户")
    @PostMapping("/user/add")
    public ApiResult addUser(@Validated @RequestBody UserDo user){
        dataService.addUser(user);
        return format(SUCCESS);
    }

    @ApiOperation("获取状态枚举")
    @GetMapping("/user/qry-status")
    public ApiResult qryStatusType(){
        return format(SUCCESS, dataService.qryStatusType());
    }

    @ApiOperation("匹配特殊字符")
    @GetMapping("/tool/match")
    public ApiResult matchSpecialCharacter(@ApiParam(name = "matchString", value = "待匹配字段", required = true) @NotBlank(message = "带匹配字段不能为空") @RequestParam(value = "matchString") String matchString){
        log.info("matchSpecialCharacter matchString:{}", matchString);
        return format(dataService.matchSpecialCharacter(matchString));
    }

    @ApiOperation("下载excel文件")
    @GetMapping("/user/export-excel")
    public void exportUserExcel(HttpServletResponse response) throws Exception{
        dataService.exportUserExcel(response.getOutputStream());
        response.setContentType("text/html; charset=UTF-8");
        response.setContentType("application/octet-stream; charset=utf-8");
        response.setHeader("content-disposition", "attachment;filename="+ URLEncoder.encode("用户列表.xls", CHARSET_UTF_8));
    }




}
