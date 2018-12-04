package com.example.dictionary.model.dataObject;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@TableName("user")
@ApiModel(value = "user", description = "用户列表模型")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserDo {
    @ApiModelProperty(name = "id", value = "主键")
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    @ApiModelProperty(name = "account", value = "账户名", required = true)
    @NotBlank(message = "账户名不能为空")
    private String account;

    @ApiModelProperty(name = "name", value = "用户名", required = true)
    @NotBlank(message = "用户名不能为空")
    private String name;

    @ApiModelProperty(name = "eMail", value = "电子邮件", required = true)
    private String eMail;
}
