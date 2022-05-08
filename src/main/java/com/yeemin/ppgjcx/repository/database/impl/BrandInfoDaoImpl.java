package com.yeemin.ppgjcx.repository.database.impl;

import com.yeemin.ppgjcx.repository.database.BrandInfoDao;
import com.yeemin.ppgjcx.repository.entity.BrandInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BrandInfoDaoImpl implements BrandInfoDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Cacheable(value = "stable", key = "'brand.info.' + #id")
    public BrandInfo queryById(Integer id) {
        return jdbcTemplate.queryForObject("select * from brand_info where id = '" + id + "'", BrandInfo.class);
    }
    
}
