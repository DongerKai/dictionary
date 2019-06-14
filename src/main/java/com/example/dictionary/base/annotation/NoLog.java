package com.example.dictionary.base.annotation;

import java.lang.annotation.*;

/**
 * no log
 *
 * @author DongerKai
 * @since 2019/6/14 10:45 ，1.0
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface NoLog {
}
