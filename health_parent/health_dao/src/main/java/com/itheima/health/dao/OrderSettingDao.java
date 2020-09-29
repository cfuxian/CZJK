package com.itheima.health.dao;

import com.itheima.health.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: chenfuxian
 * @Date: 2020/9/22 19:59
 */
public interface OrderSettingDao {
    OrderSetting findByOrderDate(Date orderDate);

    void updateNumber(OrderSetting orderSetting);

    void add(OrderSetting orderSetting);

    List<Map<String, Integer>> getOrderSettingByMonth(@Param("dateBegin") String dateBegin, @Param("dateEnd") String dateEnd);

    /**
     * 更新已预约人数
     */
    int editReservationsByOrderDate(OrderSetting orderSetting);

}
