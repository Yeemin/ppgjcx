package com.yeemin.ppgjcx.repository.database.impl;

import com.yeemin.ppgjcx.repository.database.CompanyInfoDao;
import com.yeemin.ppgjcx.repository.entity.CompanyInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CompanyInfoDaoImpl implements CompanyInfoDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Cacheable(value = "stable", key = "'company.info.' + #id")
    public CompanyInfo queryById(Integer id) {
        return jdbcTemplate.queryForObject("select * from company_info where id = '" + id + "'", CompanyInfo.class);
    }
    
}
