package com.yeemin.ppgjcx.dao;

import org.junit.jupiter.api.Test;

public class CountryDaoTest {

    @Test
    public void emojiTest() {
        byte[] bytes = "🇨🇳".getBytes();
        System.out.println(bytes);
        String s = new String(bytes);
        System.out.println(s);
    }
    
}
