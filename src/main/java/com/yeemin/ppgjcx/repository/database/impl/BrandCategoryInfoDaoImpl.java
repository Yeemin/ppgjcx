package com.yeemin.ppgjcx.repository.database.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.yeemin.ppgjcx.repository.database.BrandCategoryInfoDao;
import com.yeemin.ppgjcx.repository.entity.BrandCategoryInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class BrandCategoryInfoDaoImpl implements BrandCategoryInfoDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Cacheable(value = "stable", key = "'brand.category.info' + #brandId")
    public List<BrandCategoryInfo> queryByBrandId(Integer brandId) {
        String sql = "select id, brand_id brandId, category_id categoryId from brand_category_info where brand_id = ?";
        return jdbcTemplate.queryForStream(sql, new RowMapper<BrandCategoryInfo>() {

            @Override
            public BrandCategoryInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
                BrandCategoryInfo brandCategoryInfo = new BrandCategoryInfo();
                brandCategoryInfo.setId(rs.getInt("id"));
                brandCategoryInfo.setBrandId(rs.getInt("brandId"));
                brandCategoryInfo.setCategoryId(rs.getInt("categoryId"));
                return brandCategoryInfo;
            }

        }, brandId).toList();
    }

}
