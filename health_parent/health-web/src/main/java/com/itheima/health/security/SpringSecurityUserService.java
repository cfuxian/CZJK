package com.itheima.health.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.pojo.Permission;
import com.itheima.health.pojo.Role;
import com.itheima.health.pojo.User;
import com.itheima.health.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Set;

/**
 * @Author: chenfuxian
 * @Date: 2020/9/27 0:18
 */

@Component
public class SpringSecurityUserService implements UserDetailsService {

    @Reference
    private UserService userService;



    /**
     * 提供登陆用户信息  username password 权限集合 authorities
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据登陆用户名称查询用户权限信息
        //t_user -> t_user_role -> t_role -> t_role_permission -> t_permission
        //找出用户所拥有的角色，及角色下所拥有的权限集合
        //User.roles(角色集合).permissions(权限集合)

        //从数据库中根据名字查询User的信息
        User user = userService.findByUsername(username);
        if (null != user){
            //用户名及密码
            String password = user.getPassword();
            //权限集合
            ArrayList<GrantedAuthority> authorities = new ArrayList<>();

            //用户所拥有的角色
            Set<Role> roles = user.getRoles();
            SimpleGrantedAuthority sai = null;
            if (roles != null){
                for (Role role : roles) {
                    //角色用关键字
                    sai = new SimpleGrantedAuthority(role.getKeyword());
                    authorities.add(sai);
                    //权限，角色下的所有权限
                    Set<Permission> permissions = role.getPermissions();
                    if (permissions != null){
                        for (Permission permission : permissions) {
                            sai = new SimpleGrantedAuthority(permission.getKeyword());
                            authorities.add(sai);
                        }
                    }
                }
            }
            return new org.springframework.security.core.userdetails.User(username,password,authorities);
        }
        //返回null，限制访问
        return null;
    }
}
