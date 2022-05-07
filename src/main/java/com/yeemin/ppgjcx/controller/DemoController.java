package com.yeemin.ppgjcx.controller;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import com.yeemin.ppgjcx.core.LuceneOperation;
import com.yeemin.ppgjcx.entity.User;
import com.yeemin.ppgjcx.service.DemoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.jdbc.core.JdbcTemplate;
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
    CacheManager cacheManager;

    @Autowired
    private DemoService demoService;

    @RequestMapping("/add")
    public Map<String, Object> add(@RequestBody User user) {
        String id = UUID.randomUUID().toString();
        luceneOperation.index(UUID.randomUUID().toString(), user);
        Cache cache = cacheManager.getCache("demo");
        System.out.println(cacheManager.getCacheNames());
        cache.put(id, user);
        return Collections.singletonMap("id", id);
    }

    @RequestMapping("/get/{id}")
    public User get(@PathVariable("id") String id) {
        Cache cache = cacheManager.getCache("demo");
        return cache.get(id, User.class);
    }

    @RequestMapping("getCache")
    public User getCache() {
        return demoService.queryUser("asdfasfd");
    }

}
