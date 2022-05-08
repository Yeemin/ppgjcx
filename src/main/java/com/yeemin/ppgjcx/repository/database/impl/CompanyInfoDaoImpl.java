package com.yeemin.ppgjcx.repository.database.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.yeemin.ppgjcx.repository.database.CompanyInfoDao;
import com.yeemin.ppgjcx.repository.entity.CompanyInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class CompanyInfoDaoImpl implements CompanyInfoDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Cacheable(value = "stable", key = "'company.info.' + #id")
    public CompanyInfo queryById(Integer id) {
        String sql = "select id, name, logo from company_info where id = ?";
        return jdbcTemplate.queryForObject(sql, new RowMapper<CompanyInfo>() {

            @Override
            public CompanyInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
                CompanyInfo companyInfo = new CompanyInfo();
                companyInfo.setId(rs.getInt("id"));
                companyInfo.setName(rs.getString("name"));
                companyInfo.setLogo(rs.getString("logo"));
                return companyInfo;
            }
            
        }, id);
    }

}
