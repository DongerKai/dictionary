package com.example.dictionary.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.dictionary.base.model.PageInfo;
import com.example.dictionary.base.service.AbstractBaseService;
import com.example.dictionary.mapper.UserMapper;
import com.example.dictionary.model.dataObject.UserDo;
import com.example.dictionary.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl extends AbstractBaseService<UserMapper, UserDo> implements UserService {

    private UserMapper userMapper;

    @Override
    public PageInfo<UserDo> qryUserList(int pageIndex, int pageSize) {
        Page<UserDo> page = new Page<>();
        page.setRecords(userMapper.qryUserList(page));
        return formatPageInfo(page);
    }
}
