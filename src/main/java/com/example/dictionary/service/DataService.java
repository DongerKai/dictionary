package com.example.dictionary.service;

import com.example.dictionary.base.model.PageInfo;
import com.example.dictionary.common.model.ApiResult;
import com.example.dictionary.model.dataobject.UserDo;

import javax.servlet.ServletOutputStream;
import java.util.Map;

public interface DataService {

    PageInfo<UserDo> qryUserList(int pageIndex, int pageSize);

    void addUser(UserDo user);

    Map<Integer, String> qryStatusType();

    ApiResult.STATE matchSpecialCharacter(String matchString);

    void exportUserExcel(ServletOutputStream stream);

    void sendEmail(String to, String subject, String text);

}
