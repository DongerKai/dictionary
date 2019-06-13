package com.example.dictionary.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.example.dictionary.base.model.PageInfo;
import com.example.dictionary.base.properties.DictionaryProperties;
import com.example.dictionary.common.exception.DictionaryException;
import com.example.dictionary.common.model.ApiResult;
import com.example.dictionary.common.utils.CatchExceptionUtils;
import com.example.dictionary.model.dataObject.UserDo;
import com.example.dictionary.service.DataService;
import com.example.dictionary.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.example.dictionary.common.constant.StringConstant.*;
import static com.example.dictionary.common.model.ApiResult.STATE.*;

@Service
@AllArgsConstructor
@Slf4j
public class DataServiceImpl implements DataService {

    private UserService userService;
    private DictionaryProperties dictionaryProperties;
    private JavaMailSender mailSender;

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
    public Map<Integer, String> qryStatusType() {
        return Arrays.stream(UserDo.statusEnum.values()).collect(Collectors.toMap(UserDo.statusEnum::getKey,UserDo.statusEnum::getValue));
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
    public ApiResult.STATE matchSpecialCharacter(String matchString) {
        Pattern pattern = Pattern.compile(P_SPECIAL_CHARACTER);
        Matcher matcher = pattern.matcher(matchString);
        if (matcher.find())
            return SPECIAL_CHARACTER_EXIST;
        else return SUCCESS;
    }

    @Override
    public void exportUserExcel(ServletOutputStream stream) {
        List<UserDo> userList = userService.selectUserList();
        try (Workbook wb = new HSSFWorkbook()){
            Sheet sheet =wb.createSheet(USER_LIST);
            for (int i =0; i < userList.size(); i++){
                Row row = sheet.createRow(i);
                row.createCell(0).setCellValue(userList.get(i).getId());
                row.createCell(1).setCellValue(userList.get(i).getName());
                row.createCell(2).setCellValue(userList.get(i).getAccount());
                row.createCell(3).setCellValue(userList.get(i).getEMail());
            }
            wb.write(stream);
        } catch (Exception e){
            log.error("exportUserExcel error:{}", e.getMessage());
            throw DictionaryException.create(SYSTEM_ERROR);
        }
    }

    @Override
    public void sendEmail(String to, String subject, String text) {
        String from = dictionaryProperties.getFrom();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}
