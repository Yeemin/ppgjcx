package com.yeemin.ppgjcx.service;

import com.yeemin.ppgjcx.entity.User;

public interface DemoService {
    
    User queryUser(String id);

    User putUser(User user);

}
