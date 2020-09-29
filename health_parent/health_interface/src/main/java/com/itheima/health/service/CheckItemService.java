package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.CheckItem;
import com.sun.xml.internal.ws.handler.HandlerException;

import java.util.List;

/**
 * @Author: chenfuxian
 * @Date: 2020/9/18 19:30
 */
public interface CheckItemService {
    List<CheckItem> findAll();

    void add(CheckItem checkItem);

    PageResult<CheckItem> findPage(QueryPageBean queryPageBean);

    void deleteById(int id) throws HandlerException;

    CheckItem findById(int id);

    void update(CheckItem checkItem);
}
