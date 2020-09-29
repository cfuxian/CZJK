package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: chenfuxian
 * @Date: 2020/9/20 21:42
 */
public interface CheckGroupDao {
    void add(CheckGroup checkGroup);

    //参数类型相同，取别名
    void addCheckGroupItem(@Param("checkGroupId") Integer checkGroupId, @Param("checkitemId") Integer checkitemId);

    Page<CheckGroup> findByCondition(String queryString);

    List<CheckGroup> findAll();
}
