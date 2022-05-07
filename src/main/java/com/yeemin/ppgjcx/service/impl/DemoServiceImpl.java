package com.yeemin.ppgjcx.service.impl;

import com.yeemin.ppgjcx.entity.User;
import com.yeemin.ppgjcx.service.DemoService;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class DemoServiceImpl implements DemoService {

    @Override
    @Cacheable(value = "demo", key = "'demo.user' + #id")
    public User queryUser(String id) {
        User user = new User();
        user.setAddress("1111");
        user.setUsername("123123");
        user.setAge(28);
        System.out.println("query user");
        return user;
    }
    
}
