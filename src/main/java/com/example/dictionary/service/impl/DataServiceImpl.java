package com.example.dictionary.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.example.dictionary.base.model.PageInfo;
import com.example.dictionary.common.model.ApiResult;
import com.example.dictionary.common.utils.CatchExceptionUtils;
import com.example.dictionary.model.dataObject.UserDo;
import com.example.dictionary.service.DataService;
import com.example.dictionary.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.example.dictionary.common.constant.StringConstant.DATE_FORMAT_01;
import static com.example.dictionary.common.constant.StringConstant.P_SPECIAL_CHARACTER;
import static com.example.dictionary.common.model.ApiResult.STATE.*;

@Service
@AllArgsConstructor
@Slf4j
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

    private void dateChange(){
        Date today = new Date();//获取当前时间
        String todayStr = DateFormatUtils.format(today, DATE_FORMAT_01);//将Date日期转换为String，格式为"yyyy-HH-dd HH:mm:ss"
        try {
            Date todayDate = DateUtils.parseDate(todayStr, DATE_FORMAT_01);//将格式为"yyyy-HH-dd HH:mm:ss"的String转为Date形式
        } catch (Exception e){
            log.error(e.getMessage());
        }//parseDate需要捕捉异常
        Date todayDateAno = CatchExceptionUtils.apply(()->DateUtils.parseDate(todayStr, DATE_FORMAT_01));//也是str转date，自定义捕捉异常
        Date todayDay = DateUtils.truncate(today, Calendar.DAY_OF_MONTH);//截取到日，截取的地方可自定义
        Date tomorrow = DateUtils.addDays(today,1);//addxxx可以自定义添加日、时、分、秒、毫秒等，如果是往前数值填负
    }

    @Override
    public ApiResult.STATE matchSepecialCharacter(String matchString) {
        Pattern pattern = Pattern.compile(P_SPECIAL_CHARACTER);
        Matcher matcher = pattern.matcher(matchString);
        if (matcher.find())
            return SPECIAL_CHARACTER_EXIST;
        else return SUCCESS;
    }
}
