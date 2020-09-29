package com.itheima.health.controller;

import com.aliyuncs.exceptions.ClientException;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.constant.RedisMessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.utils.SMSUtils;
import com.itheima.health.utils.ValidateCodeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Author: chenfuxian
 * @Date: 2020/9/25 18:29
 */

@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    private static final Logger log = LoggerFactory.getLogger(ValidateCodeController.class);

    @Autowired
    private JedisPool jedisPool;

    /*
    * 发送手机验证码
    * */
    @PostMapping("/send4Order")
    public Result send4Order(String telephone){
        Jedis jedis = jedisPool.getResource();
        String key = RedisMessageConstant.SENDTYPE_ORDER + "_" + telephone;
        //redis中的验证码
        String codeInRedis = jedis.get(key);

        if (null == codeInRedis){
            //不存在，没法送，超时
            String code = ValidateCodeUtils.generateValidateCode(6) + "";
            //发送验证码
            try {
                log.debug("给手机号码:{} 发送验证码:{}", telephone, code);
                SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,code);
                log.debug("给手机号码:{} 发送验证码:{} 发送成功", telephone,code);
                //存入jedis，有效时间为15分钟
                jedis.setex(key,15*60,code);

                return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
            } catch (ClientException e) {
//                e.printStackTrace();
                log.error(String.format("给手机号码:%s 发送验证码：%s 发送失败",telephone,code),e);
                return new Result(true, MessageConstant.SEND_VALIDATECODE_FAIL);
            }finally {
                jedis.close();
            }
        }
        //存在：验证码已经发送过了
        return new Result(false,MessageConstant.SEND_VALIDATECODE_FAIL);
    }


    /*
     * 发送手机验证码
     * */
    @PostMapping("/send4Login")
    public Result send4Login(String telephone){
        Jedis jedis = jedisPool.getResource();
        String key = RedisMessageConstant.SENDTYPE_LOGIN + "_" + telephone;
        //redis中的验证码
        String codeInRedis = jedis.get(key);

        if (null == codeInRedis){
            //不存在，没法送，超时
            String code = ValidateCodeUtils.generateValidateCode(6) + "";
            //发送验证码
            try {
                log.debug("给手机号码:{} 发送验证码:{}", telephone, code);
                SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,code);
                log.debug("给手机号码:{} 发送验证码:{} 发送成功", telephone,code);
                //存入jedis，有效时间为15分钟
                jedis.setex(key,15*60,code);

                return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
            } catch (ClientException e) {
//                e.printStackTrace();
                log.error(String.format("给手机号码:%s 发送验证码：%s 发送失败",telephone,code),e);
                return new Result(true, MessageConstant.SEND_VALIDATECODE_FAIL);
            }finally {
                jedis.close();
            }
        }
        //存在：验证码已经发送过了
        return new Result(false,MessageConstant.SEND_VALIDATECODE_FAIL);
    }
}
