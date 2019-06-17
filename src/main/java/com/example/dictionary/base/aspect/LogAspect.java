package com.example.dictionary.base.aspect;

import com.example.dictionary.common.model.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.StringJoiner;

/**
 * 日志切面
 *
 * @author DongerKai
 * @since 2019/6/13 16:30 ，1.0
 **/
@Slf4j
@Aspect
@Component
@Order(1)
public class LogAspect {

    @Pointcut("(within(com.example.dictionary.controller.*) && !@annotation(com.example.dictionary.base.annotation.NoLog))")
    public void pointCut(){
        //do nothing
    }

    @AfterReturning(pointcut = "pointCut()", returning = "result")
    public void afterReturning(JoinPoint point, ApiResult result){
        log.info("{}|result={}", getPathAndArgs(point), result.toString());
    }

    @AfterThrowing(pointcut = "pointCut()", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Exception e){
// TODO 异常上下文
    }

    private String getPathAndArgs(JoinPoint joinPoint){
        StringJoiner joiner = new StringJoiner(",", "[", "]");
//        TODO session和context
        return String.format("%s.%s:", joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName());
    }
}
