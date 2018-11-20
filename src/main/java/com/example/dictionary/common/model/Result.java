package com.example.dictionary.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class Result {
    @ApiModelProperty(name = "code", value = "错误码", example = "0")
    private String code;

    @ApiModelProperty(name = "status", value = "状态true/false", example = "success")
    private String status;

    @ApiModelProperty(name = "message", value = "错误描述", example = "success")
    private String message;

    @ApiModelProperty(name = "data", value = "数据体", example = "{\"key\":\"value\"}")
    private Object data;

}
