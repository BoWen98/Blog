package com.bowen.blog.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @ProjectName: Blog
 * @Package: com.bowen.blog.controller
 * @ClassName: UserController
 * @Author: Bowen
 * @Description: UserController
 * @Date: 2019/7/4 22:10
 * @Version: 1.0.0
 */
@Controller
@Slf4j
public class UserController {

    @Value("${img.location}")
    private String folder;

    @GetMapping("/")
    public String simple() {
        return "simple";
    }


}
