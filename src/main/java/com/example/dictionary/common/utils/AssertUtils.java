package com.example.dictionary.common.utils;

import com.example.dictionary.common.exception.DictionaryException;
import com.example.dictionary.common.model.ApiResult.STATE;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Collections;

public class AssertUtils {

    private AssertUtils(){}

    public static void equals(String var1, String var2, STATE state){
        equals(var1, var2, state, null);
    }

    public static void equals(String var1, String var2, STATE state, String message){
        if (!StringUtils.equals(var1, var2))
            throw new DictionaryException(state, message);
    }

    public static void notNull(Object object, STATE state){
        notNull(object, state, null);
    }

    public static void notNull(Object object, STATE state, String message){
        if (object == null)
            throw new DictionaryException(state, message);
    }

    public static void isNull(Object object, STATE state){
        isNull(object, state, null);
    }

    public static void isNull(Object object, STATE state, String message){
        if (object != null)
            throw new DictionaryException(state, message);
    }

    public static void isEmpty(Collection<?> collection, STATE state){
        isEmpty(collection, state, null);
    }

    public static void isEmpty(Collection<?> collection, STATE state, String message){
        if (collection != null && !collection.isEmpty())
            throw new DictionaryException(state, message);
    }
}
