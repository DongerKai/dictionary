package com.example.dictionary.service;

import com.example.dictionary.base.model.PageInfo;
import com.example.dictionary.model.dataObject.UserDo;

public interface DataService {

    PageInfo<UserDo> qryUserList(int pageIndex, int pageSize);

    void addUser(UserDo user);

}
