package com.example.dictionary.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.dictionary.base.api.ApiState;
import com.example.dictionary.common.exception.DictionaryException;
import com.example.dictionary.common.model.ApiResult.STATE;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.List;

public class AssertUtils {

    private AssertUtils(){}

    public static void equals(String var1, String var2, ApiState state){
        equals(var1, var2, state, null);
    }

    public static void equals(String var1, String var2, ApiState state, String message){
        if (!StringUtils.equals(var1, var2))
            throw DictionaryException.create(state, message);
    }

    public static void isNotNull(Object object, ApiState state){
        isNotNull(object, state, null);
    }

    public static void isNotNull(Object object, ApiState state, String message){
        if (object == null)
            throw DictionaryException.create(state, message);
    }

    public static void isNull(Object object, STATE state){
        isNull(object, state, null);
    }

    public static void isNull(Object object, STATE state, String message){
        if (object != null)
            throw DictionaryException.create(state, message);
    }

    public static void isEmpty(Collection<?> collection, STATE state){
        isEmpty(collection, state, null);
    }

    public static void isEmpty(Collection<?> collection, STATE state, String message){
        if (collection != null && !collection.isEmpty())
            throw DictionaryException.create(state, message);
    }

    public static void isNotEmpty(Collection<?> collection, STATE state){
        isNotEmpty(collection, state, null);
    }

    public static void isNotEmpty(Collection<?> collection, STATE state, String message){
        if (collection == null || collection.isEmpty())
            throw DictionaryException.create(state, message);
    }

    public static void isBlank(String var, STATE state){
        isBlank(var, state, null);
    }

    public static void isBlank(String var, STATE state, String message){
        if (StringUtils.isNotBlank(var))
            throw DictionaryException.create(state, message);
    }

    public static void isNotBlank(String var, STATE state){
        isNotBlank(var, state, null);
    }

    public static void isNotBlank(String var, STATE state, String message){
        if (StringUtils.isBlank(var))
            throw DictionaryException.create(state, message);
    }

    public static void isTrue(boolean flag, STATE state){
        isTrue(flag, state, null);
    }

    public static void isTrue(boolean flag, STATE state, String message){
        if (!flag)
            throw DictionaryException.create(state, message);
    }

    public static void isFalse(boolean flag, STATE state){
        isFalse(flag, state, null);
    }

    public static void isFalse(boolean flag, STATE  state, String message){
        if (flag)
            throw DictionaryException.create(state, message);
    }

    public static JSONObject isJsonObject(String var, STATE state){ return isJsonObject(var, state, null);}

    public static JSONObject isJsonObject(String var, STATE state, String message){
        if (StringUtils.isNotBlank(var))
            try {
                return JSONObject.parseObject(var);
            }catch (Exception e){
                //
            }
        throw DictionaryException.create(state, message);
    }

    public static JSONArray isJsonArray(String var, STATE state){return isJsonArray(var, state, null);}

    public static JSONArray isJsonArray(String var, STATE state, String message){
        if (StringUtils.isNotBlank(var))
            try {
                return JSONArray.parseArray(var);
            }catch (Exception e){
                //
            }
        throw DictionaryException.create(state, message);
    }

    public static <T> List<T> parseArray(String var, STATE state, Class<T> clazz){return parseArray(var, state, clazz, null);}

    public static <T> List<T> parseArray(String var, STATE state, Class<T> clazz, String message){
        if (StringUtils.isNotBlank(var))
            try {
                return JSON.parseArray(var, clazz);
            }catch (Exception e){
                //
            }
        throw DictionaryException.create(state, message);
    }

    public static <T> T parseObject(String var, STATE state, Class<T> clazz){return parseObject(var, state, clazz, null);}

    public static <T> T parseObject(String var, STATE state, Class<T> clazz, String message){
        if (StringUtils.isNotBlank(var))
            try {
                return JSON.parseObject(var, clazz);
            }catch (Exception e){
                //
            }
        throw DictionaryException.create(state, message);
    }


}
