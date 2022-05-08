package com.yeemin.ppgjcx.repository.database.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.yeemin.ppgjcx.repository.database.CategoryInfoDao;
import com.yeemin.ppgjcx.repository.entity.CategoryInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryInfoDaoImpl implements CategoryInfoDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Cacheable(value = "stable", key = "'category.info.' + #id")
    public CategoryInfo queryById(Integer id) {
        String sql = "select id, name from category_info where id = ?";
        return jdbcTemplate.queryForObject(sql, new RowMapper<CategoryInfo> () {

            @Override
            public CategoryInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
                CategoryInfo categoryInfo = new CategoryInfo();
                categoryInfo.setId(rs.getInt("id"));
                categoryInfo.setName(rs.getString("name"));
                return categoryInfo;
            }

        }, id);
    }
    
}
