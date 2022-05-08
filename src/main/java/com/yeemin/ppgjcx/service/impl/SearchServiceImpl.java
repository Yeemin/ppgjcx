package com.yeemin.ppgjcx.service.impl;

import java.util.List;

import com.yeemin.ppgjcx.domain.vo.SearchResponseVO;
import com.yeemin.ppgjcx.domain.vo.SearchResultVO;
import com.yeemin.ppgjcx.repository.database.BrandInfoDao;
import com.yeemin.ppgjcx.repository.database.CategoryInfoDao;
import com.yeemin.ppgjcx.repository.database.CompanyCountryInfoDao;
import com.yeemin.ppgjcx.repository.database.CompanyInfoDao;
import com.yeemin.ppgjcx.repository.database.CountryInfoDao;
import com.yeemin.ppgjcx.repository.entity.BrandCompanyIndex;
import com.yeemin.ppgjcx.repository.entity.BrandInfo;
import com.yeemin.ppgjcx.repository.entity.CategoryInfo;
import com.yeemin.ppgjcx.repository.entity.CompanyCountryInfo;
import com.yeemin.ppgjcx.repository.entity.CompanyInfo;
import com.yeemin.ppgjcx.repository.entity.CountryInfo;
import com.yeemin.ppgjcx.repository.lucene.BrandCompanyIndexDao;
import com.yeemin.ppgjcx.service.SearchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private BrandCompanyIndexDao indexDao;

    @Autowired
    private BrandInfoDao brandInfoDao;

    @Autowired
    private CompanyInfoDao companyInfoDao;

    @Autowired
    private CompanyCountryInfoDao companyCountryInfoDao;

    @Autowired
    private CategoryInfoDao categoryInfoDao;

    @Autowired
    private CountryInfoDao countryInfoDao;

    @Override
    public SearchResponseVO search(String q) {
        SearchResponseVO vo = new SearchResponseVO();
        vo.setQ(q);
        List<BrandCompanyIndex> indexs = indexDao.queryByBrandName(q);
        if (indexs.size() > 0) {
            indexs.forEach(index -> {
                Integer brandId = index.getBrandId();
                BrandInfo brandInfo = brandInfoDao.queryById(brandId);
                CompanyInfo companyInfo = companyInfoDao.queryById(index.getCompanyId());
                CategoryInfo categoryInfo = categoryInfoDao.queryById(brandInfo.getCategoryId());
                List<CompanyCountryInfo> companyCountryList = companyCountryInfoDao.queryByCompany(index.getCompanyId());
                companyCountryList.forEach(companyCountry -> {
                    CountryInfo countryInfo = countryInfoDao.queryById(companyCountry.getCountryId());
                });
            });
        }

        return vo;
    }
    
}
