package com.itheima.health;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * 定时删除七牛云上未被使用的图片
 * @Author: chenfuxian
 * @Date: 2020/9/22 17:43
 */
public class JobApplication {

    public static void main(String[] args) throws IOException {
        new ClassPathXmlApplicationContext("classpath:spring-job.xml");
        System.in.read();
    }
}
