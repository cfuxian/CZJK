package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.constant.RedisMessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Order;
import com.itheima.health.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * @Author: chenfuxian
 * @Date: 2020/9/25 19:01
 */
@RestController
@RequestMapping("/order")
public class OrderMobileController {

    @Autowired
    private JedisPool jedisPool;

    @Reference
    private OrderService orderService;

/*
* 预约提交
* */
    @PostMapping("/submit")
    public Result submit(@RequestBody Map<String, String> orderInfo){
        //验证码校验
        Jedis jedis = jedisPool.getResource();
        String key = RedisMessageConstant.SENDTYPE_ORDER + "_" + orderInfo.get("telephone");
        String codeInRedis = jedis.get(key);
        //redis中未找到验证码
        if (StringUtils.isEmpty(codeInRedis)){
            return new Result(false,"请重新获取验证码");
        }
        //redis有值
        if (!codeInRedis.equals(orderInfo.get("validateCode"))){
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }

        //防止重复提交，移除验证码
        jedis.del(key);

        //验证码匹配，执行后续流程
        //设置预约类型health_mobile给手机端微信公众号使用的，写死它的类型为微信预约
        orderInfo.put("orderType", Order.ORDERTYPE_WEIXIN);
        Order order = orderService.submit(orderInfo);
        return new Result(true,MessageConstant.ORDER_SUCCESS,order);
    }


    /**
     * 订单详情
     */
    @GetMapping("/findById")
    public Result findById(int id){
        Map<String,String> orderInfo = orderService.findById(id);
        return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS,orderInfo);
    }
}
