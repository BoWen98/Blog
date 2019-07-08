package com.bowen.blog.service;

import com.bowen.blog.entity.Permission;
import com.bowen.blog.entity.SysUser;
import com.bowen.blog.mapper.PermissionMapper;
import com.bowen.blog.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: Blog
 * @Package: com.bowen.blog.service
 * @ClassName: MyUserDetailsService
 * @Author: Bowen
 * @Description: MyUserDetailService
 * @Date: 2019/7/4 14:57
 * @Version: 1.0.0
 */

@Service
@Slf4j
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser userByName = userMapper.findByUsername(username);
        if (userByName != null) {
            List<Permission> permissionList = permissionMapper.findByAdminUserId(userByName.getId());
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            for (Permission permission : permissionList) {
                if (permission != null && permission.getName() != null) {
                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getName());
                    grantedAuthorities.add(grantedAuthority);
                }
            }
            return new org.springframework.security.core.userdetails.User(userByName.getUsername(), userByName.getPassword(), grantedAuthorities);
        } else {
            log.debug("用户名为空");
            throw new UsernameNotFoundException("admin: " + username + " do not exist!");
        }
    }
}
