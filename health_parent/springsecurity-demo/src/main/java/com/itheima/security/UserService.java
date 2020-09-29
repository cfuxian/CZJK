package com.itheima.security;

import com.itheima.health.pojo.Permission;
import com.itheima.health.pojo.Role;
import com.itheima.health.pojo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author: chenfuxian
 * @Date: 2020/9/26 19:53
 */
public class UserService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userInDB = findByUsername(username);
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        //遍历用户身上的角色
        /*Set<Role> roles = userInDB.getRoles();
        if (null != roles){
            for (Role role : roles) {
                GrantedAuthority ga = new SimpleGrantedAuthority(role.getName());
                authorities.add(ga);
                for (Permission permission : role.getPermissions()) {
                    ga = new SimpleGrantedAuthority(permission.getName());
                    authorities.add(ga);
                }
            }
        }*/

        //测试其它方式的认证
        GrantedAuthority ga = new SimpleGrantedAuthority("ROLE_ADMIN");
        authorities.add(ga);
        ga = new SimpleGrantedAuthority("add");
        authorities.add(ga);

        //登录用户的认证信息，名称、密码、权限集合
        /*org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(username, "{noop}" + userInDB.getPassword(), authorities);
        return user; */
        org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(username, userInDB.getPassword(), authorities);
        return user;
    }

    private com.itheima.health.pojo.User findByUsername (String username){
        if("admin".equals(username)) {
            com.itheima.health.pojo.User user = new com.itheima.health.pojo.User();
            user.setUsername("admin");
//            user.setPassword("admin");

            // 使用密码加密器encoder, 加密后的密码
            user.setPassword("$2a$10$P7Qx8eKUPX5lngz9UEstUOaDRldEWrj9Rbyox/ShyeoxvPbEHTvni");

            Role role = new Role();
            role.setName("ROLE_ADMIN");
            Permission permission = new Permission();
            permission.setName("ADD_CHECKITEM");
            role.getPermissions().add(permission);

            Set<Role> roleList = new HashSet<Role>();
            roleList.add(role);

            user.setRoles(roleList);
            return user;
        }
        return null;
    }

}
