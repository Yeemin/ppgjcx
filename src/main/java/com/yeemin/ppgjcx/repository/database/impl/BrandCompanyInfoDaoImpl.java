package com.yeemin.ppgjcx.repository.database.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.yeemin.ppgjcx.repository.database.BrandCompanyInfoDao;
import com.yeemin.ppgjcx.repository.entity.BrandCompanyInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class BrandCompanyInfoDaoImpl implements BrandCompanyInfoDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Cacheable(value = "stable", key = "'brand.company.info' + #brandId")
    public List<BrandCompanyInfo> queryByBrandId(Integer brandId) {
        String sql = "select id, brand_id brandId, company_id companyId, percent from brand_company_info where brand_id = ?";
        return jdbcTemplate.queryForStream(sql, new RowMapper<BrandCompanyInfo>() {

            @Override
            public BrandCompanyInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
                BrandCompanyInfo brandCompanyInfo = new BrandCompanyInfo();
                brandCompanyInfo.setId(rs.getInt("id"));
                brandCompanyInfo.setBrandId(rs.getInt("brandId"));
                brandCompanyInfo.setCompanyId(rs.getInt("companyId"));
                brandCompanyInfo.setPercent(rs.getInt("percent"));
                return brandCompanyInfo;
            }
            
        }, brandId).toList();
    }

}
