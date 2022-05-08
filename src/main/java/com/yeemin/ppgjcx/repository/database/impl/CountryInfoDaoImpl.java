package com.yeemin.ppgjcx.repository.database.impl;

import com.yeemin.ppgjcx.repository.database.CountryInfoDao;
import com.yeemin.ppgjcx.repository.entity.CountryInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CountryInfoDaoImpl implements CountryInfoDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Cacheable(value = "stable", key = "'country.info.' + #id")
    public CountryInfo queryById(Integer id) {
        return jdbcTemplate.queryForObject("select * from country_info where id = '" + id + "'", CountryInfo.class);
    }
    
}
