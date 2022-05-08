package com.yeemin.ppgjcx.repository.database;

import java.util.List;

import com.yeemin.ppgjcx.repository.entity.CompanyCountryInfo;

public interface CompanyCountryInfoDao {
    
    List<CompanyCountryInfo> queryByCompany(Integer companyId);

}
