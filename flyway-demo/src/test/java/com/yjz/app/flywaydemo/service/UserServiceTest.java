package com.yjz.app.flywaydemo.service;

import com.yjz.app.flywaydemo.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void test() {
        userService.deleteAllUsers();

        // 插入5个用户
        userService.create("Tom", 10);
        userService.create("Mike", 11);
        userService.create("Didispace", 30);
        userService.create("Oscar", 21);
        userService.create("Linda", 17);

        // 查询名为Oscar的用户，判断年龄是否匹配
        List<User> userList = userService.getByName("Oscar");
        Assertions.assertEquals(21, userList.get(0).getAge().intValue());

        // 查数据库，应该有5个用户
        Assertions.assertEquals(5, userService.getAllUsers());

        // 删除两个用户
        userService.deleteByName("Tom");
        userService.deleteByName("Mike");

        // 查数据库，应该有5个用户
        Assertions.assertEquals(3, userService.getAllUsers());
    }
}