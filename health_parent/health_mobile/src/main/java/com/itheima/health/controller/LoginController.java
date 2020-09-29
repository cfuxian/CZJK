package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.constant.RedisMessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Member;
import com.itheima.health.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * @Author: chenfuxian
 * @Date: 2020/9/27 14:16
 */

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private JedisPool jedisPool;

    @Reference
    private MemberService memberService;

    @PostMapping("/check")
    public Result checkMember(@RequestBody Map<String,String> loginInfo, HttpServletResponse res){
        //先根据手机号码获得redis的验证码，与前端的验证码比对是否相同
        String telephone = loginInfo.get("telephone");
        String validateCode = loginInfo.get("validateCode");
        String key = RedisMessageConstant.SENDTYPE_LOGIN + "_" + telephone;
        //获取redis中的验证码
        Jedis jedis = jedisPool.getResource();
        String codeInRedis = jedis.get(key);
        if (codeInRedis ==null){
            //失效或者没有发送
            return new Result(false,"请点击发送验证码");
        }
        if (!codeInRedis.equals(validateCode)){
            return new Result(false,"验证码不正确");
        }
        //清除验证码，已经使用过了
        jedis.del(key);

        //判断是否为会员
        Member member = memberService.findByTelephone(telephone);
        if (null == member){
            //会员不存在
            member = new Member();
            member = new Member();
            member.setRegTime(new Date());
            member.setPhoneNumber(telephone);
            member.setRemark("手机快速注册");
            memberService.add(member);
        }

        //跟踪记录的手机号码，代表着会员
        Cookie cookie = new Cookie("login_member_telephone", telephone);
        cookie.setMaxAge(30*24*60*60);  //存一个月
        cookie.setPath("/");   //访问的路径，根路径下时，网站的所有路径，都会发送这个cookie
        res.addCookie(cookie);
        return new Result(true, MessageConstant.LOGIN_SUCCESS);

    }
}
