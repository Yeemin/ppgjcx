package com.yeemin.ppgjcx.service.impl;

import java.util.Map;

import com.yeemin.ppgjcx.entity.User;
import com.yeemin.ppgjcx.service.DemoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

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

    @Override
    @CachePut(value = "demo", key = "'demo.user' + #user.id")
    public User putUser(User user) {
        Map<String, Object> queryForMap = jdbcTemplate.queryForMap("select 1");
        System.out.println(queryForMap);
        return user;
    }

    
}
