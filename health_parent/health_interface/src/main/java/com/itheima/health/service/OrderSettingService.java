package com.itheima.health.service;

import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * @Author: chenfuxian
 * @Date: 2020/9/22 19:43
 */
public interface OrderSettingService {
    void add(List<OrderSetting> orderSettingList);

    List<Map<String, Integer>> getOrderSettingByMonth(String month);

    void editNumberByDate(OrderSetting orderSetting) throws HealthException;
}
