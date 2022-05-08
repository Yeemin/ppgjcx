package com.yeemin.ppgjcx.repository.database.impl;

import java.util.List;

import com.yeemin.ppgjcx.repository.database.CompanyCountryInfoDao;
import com.yeemin.ppgjcx.repository.entity.CompanyCountryInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CompanyCountryInfoDaoImpl implements CompanyCountryInfoDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Cacheable(value = "stable", key = "'company.country.info.' + #companyId")
    public List<CompanyCountryInfo> queryByCompany(Integer companyId) {
        return jdbcTemplate.queryForList("select * from company_country_info where company_id = '" + companyId + "'", CompanyCountryInfo.class);
    }
    
}
