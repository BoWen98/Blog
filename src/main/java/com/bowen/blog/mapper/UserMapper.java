package com.bowen.blog.mapper;


import com.bowen.blog.entity.SysUser;
import org.springframework.stereotype.Repository;


@Repository
public interface UserMapper {

    SysUser findByUsername(String username);
}