package com.example.dictionary.service;

import com.baomidou.mybatisplus.service.IService;
import com.example.dictionary.base.model.PageInfo;
import com.example.dictionary.model.dataobject.UserDo;

import java.util.List;

public interface UserService extends IService<UserDo> {

    PageInfo<UserDo> qryUserList(int pageIndex, int pageSize);

    List<UserDo> selectUserList();
}
