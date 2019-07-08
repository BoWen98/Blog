package com.bowen.blog.service.impl;

import com.bowen.blog.mapper.UserMapper;
import com.bowen.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ProjectName: Blog
 * @Package: com.bowen.blog.service.impl
 * @ClassName: UserMapperImpl
 * @Author: Bowen
 * @Description: UserServiceImpl
 * @Date: 2019/7/4 14:10
 * @Version: 1.0.0
 */
@Service
public class UserMapperImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

}
