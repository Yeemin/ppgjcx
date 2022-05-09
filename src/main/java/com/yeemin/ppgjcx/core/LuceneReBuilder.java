package com.yeemin.ppgjcx.core;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.yeemin.ppgjcx.repository.database.BrandCompanyInfoDao;
import com.yeemin.ppgjcx.repository.database.BrandInfoDao;
import com.yeemin.ppgjcx.repository.database.CompanyInfoDao;
import com.yeemin.ppgjcx.repository.entity.BrandCompanyIndex;
import com.yeemin.ppgjcx.repository.entity.BrandCompanyInfo;
import com.yeemin.ppgjcx.repository.entity.BrandInfo;
import com.yeemin.ppgjcx.repository.entity.CompanyInfo;
import com.yeemin.ppgjcx.repository.lucene.BrandCompanyIndexDao;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
public class LuceneReBuilder implements InitializingBean {

    @Autowired
    private BrandInfoDao brandInfoDao;

    @Autowired
    private BrandCompanyIndexDao indexDao;

    @Autowired
    private BrandCompanyInfoDao brandCompanyInfoDao;

    @Autowired
    private CompanyInfoDao companyInfoDao;

    @Autowired
    private CacheManager cacheManager;
    

    @Override
    public void afterPropertiesSet() throws Exception {
        Executors.newSingleThreadScheduledExecutor().schedule(() -> {
            indexDao.deleteAll();
            int count = brandInfoDao.count();
            int pageSize = 10;
            int pageTotal = count / pageSize + 1;
            for (int page = 1; page <= pageTotal; page++) {
                List<BrandInfo> list = brandInfoDao.queryPage(page, pageSize);
                list.forEach(brandInfo -> {
                    Integer brandId = brandInfo.getId();
                    List<BrandCompanyInfo> brandCompanyList = brandCompanyInfoDao.queryByBrandId(brandId);
                    brandCompanyList.forEach(brandCompany -> {
                        CompanyInfo companyInfo = companyInfoDao.queryById(brandCompany.getCompanyId());
                        BrandCompanyIndex index = new BrandCompanyIndex();
                        index.setBrandId(brandId);
                        index.setBrandName(brandInfo.getName());
                        index.setCompanyId(brandCompany.getCompanyId());
                        index.setCompanyName(companyInfo.getName());
                        indexDao.index(index);
                    });
                });
            }
        }, 10000, TimeUnit.MILLISECONDS);
        Cache cache = cacheManager.getCache("stable");
        if (cache != null) {
            cache.clear();
        }
    }
    
}
