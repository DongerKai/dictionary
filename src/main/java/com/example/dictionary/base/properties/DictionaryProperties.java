package com.example.dictionary.base.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @author DongerKai
 * @since 2019/6/6 16:49 ，1.0
 **/
@Data
@Component
@ConfigurationProperties(prefix = "dictionary")
public class DictionaryProperties {
    private String from;
}
