package com.itheima.health.service;

import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.Order;

import java.util.Map;

/**
 * @Author: chenfuxian
 * @Date: 2020/9/25 19:34
 */
public interface OrderService {
    Order submit(Map<String, String> orderInfo) throws HealthException;

    Map<String, String> findById(int id);
}
