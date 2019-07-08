package com.bowen.blog.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "sys_permission")
public class Permission {

    //主键id
    @Id
    private int id;
    //权限名称
    private String name;

    //权限描述
    private String descritpion;

    //授权链接
    private String url;

    //父节点id
    private int pid;

    //请求方式
    private String method;
}