package com.yeemin.ppgjcx.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.yeemin.ppgjcx.domain.vo.CompanyVO;
import com.yeemin.ppgjcx.domain.vo.CountryVO;
import com.yeemin.ppgjcx.domain.vo.SearchResponseVO;
import com.yeemin.ppgjcx.domain.vo.SearchResultVO;
import com.yeemin.ppgjcx.repository.database.BrandCategoryInfoDao;
import com.yeemin.ppgjcx.repository.database.BrandCompanyInfoDao;
import com.yeemin.ppgjcx.repository.database.BrandInfoDao;
import com.yeemin.ppgjcx.repository.database.CategoryInfoDao;
import com.yeemin.ppgjcx.repository.database.CompanyCountryInfoDao;
import com.yeemin.ppgjcx.repository.database.CompanyInfoDao;
import com.yeemin.ppgjcx.repository.database.CountryInfoDao;
import com.yeemin.ppgjcx.repository.entity.BrandCategoryInfo;
import com.yeemin.ppgjcx.repository.entity.BrandCompanyIndex;
import com.yeemin.ppgjcx.repository.entity.BrandCompanyInfo;
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

    @Autowired
    private BrandCategoryInfoDao brandCategoryInfoDao;

    @Autowired
    private BrandCompanyInfoDao brandCompanyInfoDao;

    @Override
    public SearchResponseVO search(String q) {
        SearchResponseVO vo = new SearchResponseVO();
        vo.setQ(q);
        List<BrandCompanyIndex> indexs = indexDao.queryByBrandName(q);
        List<SearchResultVO> resultVOS = new ArrayList<>();
        if (indexs.size() > 0) {
            indexs.forEach(index -> {
                SearchResultVO resultVO = new SearchResultVO();
                Integer brandId = index.getBrandId();
                // 品牌信息
                BrandInfo brandInfo = brandInfoDao.queryById(brandId);
                resultVO.setBrand(brandInfo.getName());
                // 品牌对应的分类信息
                List<BrandCategoryInfo> brandCategoryList = brandCategoryInfoDao.queryByBrandId(brandId);
                resultVO.setCategory(brandCategoryList.stream().map(BrandCategoryInfo::getCategoryId)
                        .distinct().map(categoryId -> categoryInfoDao.queryById(categoryId))
                        .map(CategoryInfo::getName).toList());
                // 品牌对应公司信息
                List<CompanyVO> companysVOS = new ArrayList<>();
                Set<CountryVO> countryVOSet = new HashSet<>();
                List<BrandCompanyInfo> brandCompanyList = brandCompanyInfoDao.queryByBrandId(brandId);
                brandCompanyList.forEach(brandCompany -> {
                    // 公司信息
                    CompanyInfo companyInfo = companyInfoDao.queryById(brandCompany.getCompanyId());
                    CompanyVO companyVO = new CompanyVO();
                    companyVO.setName(companyInfo.getName());
                    companyVO.setLogo(companyInfo.getLogo());
                    List<CountryVO> countryVOs = new ArrayList<>();
                    // 公司对应的国家信息
                    List<CompanyCountryInfo> companyCountryList = companyCountryInfoDao
                            .queryByCompany(companyInfo.getId());
                    // 国家信息
                    companyCountryList.forEach(companyCountry -> {
                        CountryInfo countryInfo = countryInfoDao.queryById(companyCountry.getCountryId());
                        CountryVO countryVO = new CountryVO();
                        countryVO.setName(countryInfo.getName());
                        countryVO.setFlag(countryInfo.getFlag());
                        countryVO.setPercent(companyCountry.getPercent());
                        countryVOs.add(countryVO);
                        countryVOSet.add(countryVO);
                    });
                    companyVO.setCountry(countryVOs);
                    companysVOS.add(companyVO);
                });
                resultVO.setCompany(companysVOS);
                resultVO.setCountry(new ArrayList<>(countryVOSet));
                resultVOS.add(resultVO);
            });
        }
        vo.setResult(resultVOS);
        return vo;
    }

}
