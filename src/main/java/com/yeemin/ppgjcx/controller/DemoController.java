package com.yeemin.ppgjcx.controller;

import java.util.Collections;
import java.util.List;
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
        luceneOperation.index(user);
        demoService.putUser(user);
        return Collections.singletonMap("id", id);
    }

    @RequestMapping("/get/{id}")
    public User get(@PathVariable("id") String id) {
        return demoService.queryUser(id);
    }

    @RequestMapping("/query")
    public User query(@RequestBody User criteria) {
        String address = criteria.getAddress();
        String id = searchUserByAddress(address);
        if (id != null) {
            return demoService.queryUser(id);
        }
        return null;
    }

    private String searchUserByAddress(String address) {
        List<Map<String, String>> list = luceneOperation.search("address", address);
        if (!list.isEmpty()) {
            Map<String, String> map = list.get(0);
            return map.get("id");
        }
        return null;
    }

}
