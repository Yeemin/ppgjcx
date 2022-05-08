package com.yeemin.ppgjcx.repository.database;

import java.util.List;

import com.yeemin.ppgjcx.repository.entity.BrandCompanyInfo;

public interface BrandCompanyInfoDao {
    
    List<BrandCompanyInfo> queryByBrandId(Integer brandId);

}
