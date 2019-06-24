package com.example.dictionary.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.example.dictionary.model.dataobject.UserDo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper extends BaseMapper<UserDo> {

    @Select("SELECT * FROM user")
    List<UserDo> qryUserList(Pagination page);

    @Select("SELECT * FROM user")
    List<UserDo> selectUserList();
}
