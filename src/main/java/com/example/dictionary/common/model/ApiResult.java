package com.example.dictionary.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

import static com.example.dictionary.common.constant.StringConstant.DATE_FORMAT_01;
import static com.example.dictionary.common.constant.StringConstant.GMT_8;

@Data
@ApiModel(value = "ApiResult", description = "rest接口统一返回格式")
public class ApiResult {
    @ApiModelProperty(name = "code", value = "错误码", example = "0")
    private int code;

    @ApiModelProperty(name = "status", value = "状态true/false", example = "true")
    private boolean status;

    @ApiModelProperty(name = "message", value = "错误描述", example = "success")
    private String message;

    @ApiModelProperty(name = "data", value = "数据体", example = "{\"key\":\"value\"}")
    private Object data;

    @ApiModelProperty(name = "timestamp", value = "时间戳", example = "2018-11-20 00:00:00")
    @JsonFormat(pattern = DATE_FORMAT_01, timezone = GMT_8)
    private Date timestamp;

    private ApiResult(int code, boolean status, String message, Object data){
        this.code = code;
        this.status = status;
        this.message = message;
        this.data = data;
        this.timestamp = new Date();
    }

    private ApiResult(){}

    public ApiResult(STATE state){
        this(state.getCode(), state.isStatus(), state.getMessage(), null);
    }

    public ApiResult(STATE state, Object data){
        this(state.getCode(), state.isStatus(), state.getMessage(), data);
    }

    public ApiResult(STATE state, String message, Object data){
        this(state.getCode(), state.isStatus(), message, data);
    }

    public static ApiResult format(int code, boolean status, String message, Object data){
        ApiResult apiResult = new ApiResult();
        apiResult.setCode(code);
        apiResult.setStatus(status);
        apiResult.setMessage(message);
        apiResult.setData(data);
        apiResult.setTimestamp(new Date());
        return apiResult;
    }

    public static ApiResult format(STATE state){
        return format(state.getCode(), state.isStatus(), state.getMessage(), null);
    }

    public static ApiResult format(STATE state, Object data){
        return format(state.getCode(), state.isStatus(), state.getMessage(), data);
    }

    public static ApiResult format(STATE state, String message, Object data){
        return format(state.getCode(), state.isStatus(), message, data);
    }

    @AllArgsConstructor
    @Getter
    public enum STATE{

        SUCCESS(0, true, "success"),
        INVALID_PARAM(101, false, "invalid param"),
        INVALID_PATH(102, false, "请求方式有误！"),
        SYSTEM_ERROR(9999, false, "system error");


        private int code;
        private boolean status;
        private String message;
    }


}
