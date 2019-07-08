package com.bowen.blog.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "sys_role")
public class SysRole {
    //主键id
    @Id
    private Integer id;

    //角色名称
    private String name;
}