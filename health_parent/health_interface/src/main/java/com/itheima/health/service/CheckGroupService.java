package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.CheckGroup;

import java.util.List;

/**
 * @Author: chenfuxian
 * @Date: 2020/9/20 21:28
 */
public interface CheckGroupService {


    PageResult<CheckGroup> findPage(QueryPageBean queryPageBean);

    void add(CheckGroup checkGroup, Integer[] checkitemIds);

    List<CheckGroup> findAll();

}
