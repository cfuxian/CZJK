package com.itheima.health.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.OrderSettingDao;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.OrderSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: chenfuxian
 * @Date: 2020/9/22 19:43
 */
@Service(interfaceClass = OrderSettingService.class)
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Override
    @Transactional
    public void add(List<OrderSetting> orderSettingList) {
        for (OrderSetting orderSetting : orderSettingList) {
            //先判断日期是否存在
            OrderSetting osInDB = orderSettingDao.findByOrderDate(orderSetting.getOrderDate());
            if (osInDB != null){
                //数据库中，已预约数量不能大于可预约数量
                if (osInDB.getReservations() > orderSetting.getNumber()){
                    throw new HealthException(orderSetting.getOrderDate() + " 中已预约数量不能大于可预约数量");
                }
                orderSettingDao.updateNumber(orderSetting);
            }else{
                orderSettingDao.add(orderSetting);
            }


        }
    }

    @Override
    public List<Map<String, Integer>> getOrderSettingByMonth(String month) {
        String dateBegin = month + "-1";
        String dateEnd = month + "-31";
        List<Map<String, Integer>> monthData = orderSettingDao.getOrderSettingByMonth(dateBegin,dateEnd);


        return monthData;
    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) throws HealthException {
        //通过日期判断预约设置是否存在
        OrderSetting os = orderSettingDao.findByOrderDate(orderSetting.getOrderDate());
//先判断预约设置是否存在
        if (null != os){
            // 判断已经预约的人数是否大于要更新的最大可预约人数， reverations > 传进来的number数量，则不能更新，要报错
            if (os.getReservations() > orderSetting.getNumber()){
                throw new HealthException("最大预约人数不能小于已预约人数");
            }
            orderSettingDao.updateNumber(orderSetting);
        }else {
            //不存在
            orderSettingDao.add(orderSetting);
        }

    }
}
