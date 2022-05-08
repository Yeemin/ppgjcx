package com.yeemin.ppgjcx.repository.database;

import java.util.List;

import com.yeemin.ppgjcx.repository.entity.BrandCategoryInfo;

public interface BrandCategoryInfoDao {
    
    List<BrandCategoryInfo> queryByBrandId(Integer brandId);

}
