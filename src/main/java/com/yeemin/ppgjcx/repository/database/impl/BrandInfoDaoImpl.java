package com.yeemin.ppgjcx.repository.database.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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

    @Override
    public int count() {
        String sql = "select count(1) count from brand_info";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public List<BrandInfo> queryPage(int page, int pageSize) {
        String sql = "select id, name from brand_info limit ?, ?";
        return jdbcTemplate.queryForStream(sql, new RowMapper<BrandInfo>() {

            @Override
            public BrandInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
                BrandInfo brandInfo = new BrandInfo();
                brandInfo.setId(rs.getInt("id"));
                brandInfo.setName(rs.getString("name"));
                return brandInfo;
            }

        }, (page - 1) * pageSize, pageSize).toList();
    }
    
}
