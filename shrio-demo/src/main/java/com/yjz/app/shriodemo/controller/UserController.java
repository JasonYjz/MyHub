package com.yjz.app.shriodemo.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/admin")
public class UserController {

    @PostMapping(path = "/login")
    public String login() {
        System.out.println("login");
        Subject subject = SecurityUtils.getSubject();
        subject.login(new UsernamePasswordToken("admin", "admin"));

        return "login";
    }

    @RequiresRoles("admin")
    @GetMapping(path = "/getlist")
    public String getList() {
        System.out.println("start to get list.");

        Subject subject = SecurityUtils.getSubject();

        System.out.println(subject.isAuthenticated());
        System.out.println(subject.hasRole("admin"));
        System.out.println(subject.getSession().getId());

        return "getList";
    }

    @RequiresRoles("xxx")
    @GetMapping(path = "/getno")
    public String getNo() {
        System.out.println("start to get no.");

        Subject subject = SecurityUtils.getSubject();

        System.out.println(subject.isAuthenticated());
        System.out.println(subject.hasRole("admin"));
        System.out.println(subject.getSession().getId());

        return "getno";
    }
}
