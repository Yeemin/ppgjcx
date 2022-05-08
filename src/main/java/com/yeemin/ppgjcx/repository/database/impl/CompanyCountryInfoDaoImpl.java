package com.yeemin.ppgjcx.repository.database.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.yeemin.ppgjcx.repository.database.CompanyCountryInfoDao;
import com.yeemin.ppgjcx.repository.entity.CompanyCountryInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class CompanyCountryInfoDaoImpl implements CompanyCountryInfoDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Cacheable(value = "stable", key = "'company.country.info.' + #companyId")
    public List<CompanyCountryInfo> queryByCompany(Integer companyId) {
        String sql = "select id, company_id companyId, country_id countryId, percent from company_country_info where company_id = ?";
        return jdbcTemplate.queryForStream(sql, new RowMapper<CompanyCountryInfo>() {

            @Override
            public CompanyCountryInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
                CompanyCountryInfo companyCountryInfo = new CompanyCountryInfo();
                companyCountryInfo.setId(rs.getInt("id"));
                companyCountryInfo.setCompanyId(rs.getInt("companyId"));
                companyCountryInfo.setCountryId(rs.getInt("countryId"));
                companyCountryInfo.setPercent(rs.getInt("percent"));
                return companyCountryInfo;
            }
            
        }, companyId).toList();
    }
    
}
