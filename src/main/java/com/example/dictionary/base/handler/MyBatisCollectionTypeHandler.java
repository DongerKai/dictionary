package com.example.dictionary.base.handler;

import com.alibaba.fastjson.JSONArray;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * mybatis java集合映射数据库VARCHAR 数据库类型JSON
 */
@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes({List.class, Set.class, Queue.class , ArrayList.class})
public class MyBatisCollectionTypeHandler extends BaseTypeHandler<Collection> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Collection collection, JdbcType jdbcType) throws SQLException {
        ps.setString(i, JSONArray.toJSONString(collection));
    }

    @Override
    public Collection getNullableResult(ResultSet rs, String s) throws SQLException {
        return JSONArray.parseArray(rs.getString(s));
    }

    @Override
    public Collection getNullableResult(ResultSet rs, int i) throws SQLException {
        return JSONArray.parseArray(rs.getString(i));
    }

    @Override
    public Collection getNullableResult(CallableStatement cs, int i) throws SQLException {
        return JSONArray.parseArray(cs.getString(i));
    }

}
