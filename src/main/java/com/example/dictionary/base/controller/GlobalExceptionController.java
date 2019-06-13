package com.example.dictionary.base.controller;

import com.example.dictionary.common.exception.DictionaryException;
import com.example.dictionary.common.model.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.dictionary.common.constant.StringConstant.CONNECTOR;
import static com.example.dictionary.common.model.ApiResult.STATE.INVALID_PARAM;
import static com.example.dictionary.common.model.ApiResult.STATE.INVALID_PATH;
import static com.example.dictionary.common.model.ApiResult.STATE.SYSTEM_ERROR;

@RestController
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class GlobalExceptionController extends AbstractErrorController {

    public GlobalExceptionController(ErrorAttributes errorAttributes){
        super(errorAttributes);
    }

    /**
     * 前端校验异常
     */
    @ExceptionHandler({ConstraintViolationException.class, MethodArgumentNotValidException.class, HttpMessageNotReadableException.class, MissingServletRequestParameterException.class})
    public ResponseEntity frontVerify(Exception e){
        log.warn("参数校验异常:{}",e.getMessage());
        String message = null;
        if (e instanceof MethodArgumentNotValidException){
            MethodArgumentNotValidException temp = (MethodArgumentNotValidException)e;
            message = temp.getBindingResult().getAllErrors().stream().map(row -> row == null ? null : row.getDefaultMessage()).collect(Collectors.joining(CONNECTOR));//返回全错误
            /*Iterator var2 = temp.getBindingResult().getAllErrors().iterator();
            message = var2.hasNext()?((ObjectError)var2.next()).getDefaultMessage() : null;//只返回一个错误*/
        }else if (e instanceof ConstraintViolationException){
            ConstraintViolationException temp = (ConstraintViolationException)e;
            Optional<ConstraintViolation<?>> op = temp.getConstraintViolations().stream().findFirst();
            if (op.isPresent())
                message = op.get().getMessage();//只返回一个错误
//            message = e.getMessage();//返回全部错误
        }else if (e instanceof MissingServletRequestParameterException){
            MissingServletRequestParameterException temp = (MissingServletRequestParameterException)e;
            message = INVALID_PARAM.getMessage() + " " + temp.getParameterName();
        }
        ApiResult result = ApiResult.format(INVALID_PARAM);
        if (StringUtils.isNotBlank(message))
            result.setMessage(message);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 业务异常
     */

    @ExceptionHandler(DictionaryException.class)
    public ResponseEntity dicExceptionHandler(DictionaryException e){
        log.warn("处理异常|{}|{}", e.getState(), e.getMessage());
        ApiResult result = ApiResult.format(e.getState());
        if (StringUtils.isNotBlank(e.getMessage()))
            result.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 大范围异常
     */

    @ExceptionHandler(Exception.class)
    public ResponseEntity exceptionHandler(Exception e){
        return wrapperException(e, SYSTEM_ERROR);
    }

    /**
     * 请求方式异常
     */

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity exceptionHandler(HttpRequestMethodNotSupportedException e){
        return wrapperException(e, INVALID_PATH);
    }

    private ResponseEntity wrapperException(Exception e, ApiResult.STATE state){
        log.error("系统异常:{}", e);
        ApiResult result = ApiResult.format(state);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
