package com.example.dictionary.base.service;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.dictionary.base.model.PageInfo;

public abstract class AbstractBaseService<M extends BaseMapper<T>, T> extends ServiceImpl<M , T> {



    public PageInfo<T> formatPageInfo(Page<T> page){
        PageInfo<T> pageInfo = new PageInfo<>();
        pageInfo.setList(page.getRecords());
        pageInfo.setPageIndex(page.getCurrent());
        pageInfo.setPageSize(page.getSize());
        pageInfo.setCount(page.getTotal());
        pageInfo.setPageTotal(page.getPages());
        return pageInfo;
    }

}
