package com.example.dictionary.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * base64文件实体
 *
 * @author DongerKai
 * @since 2019/6/13 11:20 ，1.0
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Base64File", description = "base64文件模型")
public class Base64FileDto {
    @ApiModelProperty(name = "name", value = "文件名称", required = true)
    @NotBlank(message = "文件名称不能为空！")
    private String name;
    @ApiModelProperty(name = "body", value = "文件主体", required = true)
    @NotBlank(message = "文件主体不能为空！")
    private String body;
    @ApiModelProperty(name = "contentType", value = "内容类型", required = true)
    @NotBlank(message = "文件内容类型不能为空！")
    private String contentType;
}
