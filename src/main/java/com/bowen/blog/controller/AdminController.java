package com.bowen.blog.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @ProjectName: Blog
 * @Package: com.bowen.blog.controller
 * @ClassName: UserController
 * @Author: Bowen
 * @Description: UserController
 * @Date: 2019/7/4 14:13
 * @Version: 1.0.0
 */
@RequestMapping("admin")
@Controller
public class AdminController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/login/error", method = RequestMethod.POST)
    public String loginError(Model model) {
        model.addAttribute("message", "用户名或密码错误请重新输入");
        return "login";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/save", method = RequestMethod.GET)
    public String goIndex() {
        return "save";
    }
}
