package com.yeemin.ppgjcx.repository.database.impl;

import com.yeemin.ppgjcx.repository.database.CategoryInfoDao;
import com.yeemin.ppgjcx.repository.entity.CategoryInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryInfoDaoImpl implements CategoryInfoDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Cacheable(value = "stable", key = "'category.info.' + #id")
    public CategoryInfo queryById(Integer id) {
        return jdbcTemplate.queryForObject("select * from category_info where id = '" + id + "'", CategoryInfo.class);
    }
    
}
