package com.example.dictionary.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.example.dictionary.service.DataService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataServiceImpl implements DataService {

    private void List2String(List<Object> list){
        String str = JSONArray.toJSONString(list);
    }//列表转字符串

    private void String2List(String str){
        List<Object> list = JSONArray.parseArray(str, Object.class);
    }//字符串转列表,object可替换为指定对象实体类
}
