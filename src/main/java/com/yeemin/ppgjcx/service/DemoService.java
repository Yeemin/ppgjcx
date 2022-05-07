package com.yeemin.ppgjcx.service;

import com.yeemin.ppgjcx.domain.User;

public interface DemoService {
    
    User queryUser(String id);

    User putUser(User user);

}
