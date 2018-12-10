package com.example.dictionary.common.utils;

import com.example.dictionary.common.exception.DictionaryException;
import lombok.extern.slf4j.Slf4j;

import static com.example.dictionary.common.model.ApiResult.STATE.SYSTEM_ERROR;

@Slf4j
public class CatchExceptionUtils {

    private CatchExceptionUtils(){}

    public static <T> T apply(Action<T> action){
        try {
            return action.apply();
        } catch (Exception e){
            throw new DictionaryException(SYSTEM_ERROR, e.getMessage());
        }
    }

    public interface Action<T> {
        T apply() throws Exception;
    }
}
