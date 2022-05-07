package com.yeemin.ppgjcx.lucene;

import java.util.List;

import com.yeemin.ppgjcx.repository.entity.BrandCompanySearchInfo;
import com.yeemin.ppgjcx.repository.lucene.BrandCompanySearchQueryer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BrandCompanySearchQueryerTests {
    
    @Autowired
    private BrandCompanySearchQueryer queryer;

    @Test
    public void index() {
        BrandCompanySearchInfo brandCompanySearchInfo = new BrandCompanySearchInfo();
        brandCompanySearchInfo.setBrandId(1);
        brandCompanySearchInfo.setBrandName("华为手机");
        brandCompanySearchInfo.setCompanyId(1);
        brandCompanySearchInfo.setCompanyName("华为技术有限公司");
        queryer.index(brandCompanySearchInfo);
    }

    @Test
    public void update() {
        BrandCompanySearchInfo brandCompanySearchInfo = new BrandCompanySearchInfo();
        brandCompanySearchInfo.setBrandId(1);
        brandCompanySearchInfo.setBrandName("华为手机");
        brandCompanySearchInfo.setCompanyId(1);
        brandCompanySearchInfo.setCompanyName("中国华为技术有限公司");
        queryer.update(brandCompanySearchInfo);
    }

    @Test       
    public void queryByBrandName() {
        List<BrandCompanySearchInfo> list = queryer.queryByBrandName("华为");
        System.out.println(list);
    }

    @Test
    public void queryByCompanyName() {
        List<BrandCompanySearchInfo> list = queryer.queryByCompanyName("中国");
        System.out.println(list);
    }

    @Test
    public void deleteByBrandId() {
        queryer.deleteByBrandId(1);
    }



}
