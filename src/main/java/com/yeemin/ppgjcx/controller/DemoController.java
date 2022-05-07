package com.yeemin.ppgjcx.controller;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import com.yeemin.ppgjcx.core.LuceneOperation;
import com.yeemin.ppgjcx.entity.User;
import com.yeemin.ppgjcx.service.DemoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("demo")
public class DemoController {

    @Autowired
    private LuceneOperation luceneOperation;

    @Autowired
    private DemoService demoService;

    @RequestMapping("/add")
    public Map<String, Object> add(@RequestBody User user) {
        String id = UUID.randomUUID().toString();
        user.setId(id);
        luceneOperation.index(UUID.randomUUID().toString(), user);
        demoService.putUser(user);
        return Collections.singletonMap("id", id);
    }

    @RequestMapping("/get/{id}")
    public User get(@PathVariable("id") String id) {
        return demoService.queryUser(id);
    }

}
