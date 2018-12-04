package com.example.dictionary.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.example.dictionary.base.model.PageInfo;
import com.example.dictionary.model.dataObject.UserDo;
import com.example.dictionary.service.DataService;
import com.example.dictionary.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DataServiceImpl implements DataService {

    private UserService userService;

    private void List2String(List<Object> list){
        String str = JSONArray.toJSONString(list);
    }//列表转字符串

    private void String2List(String str){
        List<Object> list = JSONArray.parseArray(str, Object.class);
    }//字符串转列表,object可替换为指定对象实体类

    @Override
    public PageInfo<UserDo> qryUserList(int pageIndex, int pageSize) {
        return userService.qryUserList(pageIndex, pageSize);
    }

    @Override
    public void addUser(UserDo user) {
        userService.insert(user);
    }

    @Override
    public Map<String, String> qryStatusType() {
        return Arrays.stream(UserDo.statusEnum.values()).collect(Collectors.toMap(UserDo.statusEnum::getCode,UserDo.statusEnum::getMessage));
    }
}
