package com.example.dictionary.base.model;

import com.baomidou.mybatisplus.plugins.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageInfo<T> {
    private List<T> list;
    private int pageIndex;
    private int pageSize;
    private long count;//总条数
    private long pageTotal;//总页数

    public static <T> PageInfo<T> format(List<T> list, int pageIndex, int pageSize, long count, long pageTotal){
        PageInfo<T> pageInfo = new PageInfo<>();
        pageInfo.setList(list);
        pageInfo.setPageIndex(pageIndex);
        pageInfo.setPageSize(pageSize);
        pageInfo.setCount(count);
        pageInfo.setPageTotal(pageTotal);
        return pageInfo;
    }

    public PageInfo(Page<T> page){
        super();
        this.list = page.getRecords();
        this.pageIndex = page.getCurrent();
        this.pageSize = page.getSize();
        this.count = page.getTotal();
        this.pageTotal = page.getPages();
    }

}
