package com.yeemin.ppgjcx.repository.database.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.yeemin.ppgjcx.repository.database.BrandInfoDao;
import com.yeemin.ppgjcx.repository.entity.BrandInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class BrandInfoDaoImpl implements BrandInfoDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Cacheable(value = "stable", key = "'brand.info.' + #id")
    public BrandInfo queryById(Integer id) {
        String sql = "select id, name from brand_info where id = ?";
        // List<BrandInfo> list = jdbcTemplate.queryForList(sql, BrandInfo.class, id);
        // if (list != null && list.size() > 0) {
        //     return list.get(0);
        // }
        return jdbcTemplate.queryForObject(sql, new RowMapper<BrandInfo>() {

            @Override
            public BrandInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
                BrandInfo brandInfo = new BrandInfo();
                brandInfo.setId(rs.getInt("id"));
                brandInfo.setName(rs.getString("name"));
                return brandInfo;
            }

        }, id);
    }
    
}
