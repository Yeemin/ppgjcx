package com.yeemin.ppgjcx.controller;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import com.yeemin.ppgjcx.core.LuceneOperation;
import com.yeemin.ppgjcx.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("demo")
public class DemoController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private LuceneOperation luceneOperation;

    @RequestMapping("/add")
    public Map<String, Object> add(@RequestBody User user) {
        luceneOperation.index(UUID.randomUUID().toString(), user);
        return Collections.singletonMap("code", "200");
    }

}
