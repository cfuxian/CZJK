package com.itheima.health.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.pojo.User;

/**
 * @Author: chenfuxian
 * @Date: 2020/9/27 0:46
 */

public interface UserService {

    User findByUsername(String username);
}
