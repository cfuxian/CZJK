package com.itheima.health.dao;

import com.itheima.health.pojo.User;

/**
 * @Author: chenfuxian
 * @Date: 2020/9/27 0:51
 */
public interface UserDao {
    User findByUsername(String username);
}
