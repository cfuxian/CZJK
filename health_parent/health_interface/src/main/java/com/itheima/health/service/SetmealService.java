package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.Setmeal;

import java.util.List;
import java.util.Map;

/**
 * @Author: chenfuxian
 * @Date: 2020/9/21 19:32
 */
public interface SetmealService {
    void add(Setmeal setmeal, Integer[] checkgroupIds);

    PageResult<Setmeal> findPage(QueryPageBean queryPageBean);

    Setmeal findById(int id);

    List<Integer> findCheckgroupIdsBySetmealId(int id);

    void update(Setmeal setmeal, Integer[] checkgroupIds);

    List<String> findImgs();

    Setmeal findDetailById(int id);

    List<Setmeal> findAll();

    void deleteById(Integer id) throws HealthException;

    List<Map<String, Object>> findSetmealCount();
}
