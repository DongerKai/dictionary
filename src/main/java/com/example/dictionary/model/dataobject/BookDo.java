package com.example.dictionary.model.dataobject;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 测试RabbitMQ
 *
 * @author DongerKai
 * @since 2019/6/21 9:36 ，1.0
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "BookDo", description = "书籍实体")
public class BookDo implements Serializable {
    private static final long serialVersionUID = -4332977162934869737L;
    private String id;
    private String name;
}
