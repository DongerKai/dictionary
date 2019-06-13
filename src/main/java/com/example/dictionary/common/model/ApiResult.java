package com.example.dictionary.common.model;

import com.example.dictionary.base.api.ApiState;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.ZonedDateTime;

import static com.example.dictionary.common.constant.StringConstant.DATE_FORMAT_01;
import static com.example.dictionary.common.constant.StringConstant.GMT_8;

@Data
@ApiModel(value = "ApiResult", description = "rest接口统一返回格式")
public class ApiResult<T> {
    @ApiModelProperty(name = "code", value = "错误码", example = "0")
    private int code;

    @ApiModelProperty(name = "status", value = "状态true/false", example = "true")
    private boolean status;

    @ApiModelProperty(name = "message", value = "错误描述", example = "success")
    private String message;

    @ApiModelProperty(name = "data", value = "数据体", example = "{\"key\":\"value\"}")
    private T data;

    @ApiModelProperty(name = "timestamp", value = "时间戳", example = "2018-11-20 00:00:00")
    @JsonFormat(pattern = DATE_FORMAT_01, timezone = GMT_8)
    private ZonedDateTime timestamp;

    public static<T> ApiResult<T> format(int code, boolean status, String message, T data){
        ApiResult<T> apiResult = new ApiResult<>();
        apiResult.setCode(code);
        apiResult.setStatus(status);
        apiResult.setMessage(message);
        apiResult.setData(data);
        apiResult.setTimestamp(ZonedDateTime.now());
        return apiResult;
    }

    public static <T> ApiResult<T> format(ApiState state){
        return format(state.getCode(), state.isStatus(), state.getMessage(), null);
    }

    public static <T> ApiResult<T> format(ApiState state, T data){
        return format(state.getCode(), state.isStatus(), state.getMessage(), data);
    }

    public static <T> ApiResult<T> format(ApiState state, String message, T data){
        return format(state.getCode(), state.isStatus(), message, data);
    }

    @AllArgsConstructor
    @Getter
    public enum STATE implements ApiState {

        SUCCESS(0, true, "success"),
        INVALID_PARAM(1001, false, "invalid param"),
        INVALID_PATH(1002, false, "请求方式有误！"),
        SPECIAL_CHARACTER_EXIST(2001, false, "存在特殊字符！"),
        SYSTEM_ERROR(9999, false, "system error");


        private int code;
        private boolean status;
        private String message;
    }


}
