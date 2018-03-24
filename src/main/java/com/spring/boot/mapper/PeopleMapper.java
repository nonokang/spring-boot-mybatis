package com.spring.boot.mapper;

import com.github.pagehelper.Page;
import com.spring.boot.model.People;

public interface PeopleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(People record);

    int insertSelective(People record);

    People selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(People record);

    int updateByPrimaryKey(People record);
    
    Page<People> queryList(People record);
}