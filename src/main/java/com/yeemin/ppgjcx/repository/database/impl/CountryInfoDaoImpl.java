package com.yeemin.ppgjcx.repository.database.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.yeemin.ppgjcx.repository.database.CountryInfoDao;
import com.yeemin.ppgjcx.repository.entity.CountryInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class CountryInfoDaoImpl implements CountryInfoDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Cacheable(value = "stable", key = "'country.info.' + #id")
    public CountryInfo queryById(Integer id) {
        String sql = "select * from country_info where id = ?";
        return jdbcTemplate.queryForObject(sql, new RowMapper<CountryInfo>() {

            @Override
            public CountryInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
                CountryInfo countryInfo = new CountryInfo();
                countryInfo.setId(rs.getInt("id"));
                countryInfo.setName(rs.getString("name"));
                countryInfo.setFlag(rs.getString("flag"));
                return countryInfo;
            }
            
        }, id);
    }
    
}
