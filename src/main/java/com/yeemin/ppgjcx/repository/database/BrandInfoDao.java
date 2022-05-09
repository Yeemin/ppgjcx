package com.yeemin.ppgjcx.repository.database;

import java.util.List;

import com.yeemin.ppgjcx.repository.entity.BrandInfo;

public interface BrandInfoDao {
    
    BrandInfo queryById(Integer id);

    int count();

    List<BrandInfo> queryPage(int page, int pageSize);

}
