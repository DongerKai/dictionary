package com.example.dictionary.model.dataObject;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldStrategy;
import com.baomidou.mybatisplus.enums.IdType;
import com.example.dictionary.base.api.AttrEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import java.util.List;

import static com.example.dictionary.common.constant.StringConstant.P_EMAIL;

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
    @JsonProperty("uname")
    private String name;

    @ApiModelProperty(name = "eMail", value = "电子邮件", required = true)
    @Pattern(regexp = P_EMAIL, message = "请输入正确格式的电子邮件")
    private String eMail;

    @ApiModelProperty(name = "status", value = "状态", hidden = true)
    @TableLogic
    private String status;

    @ApiModelProperty(name = "urls", value = "列表备注")
    @TableField(strategy= FieldStrategy.IGNORED)
    private List<String> urls;

    @Getter
    @AllArgsConstructor
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    public enum statusEnum implements AttrEnum {
        ON(0, "ON"),
        OFF(1, "OFF");

        private Integer key;
        private String value;
    }
}
