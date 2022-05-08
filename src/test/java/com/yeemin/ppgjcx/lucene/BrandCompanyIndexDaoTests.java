package com.yeemin.ppgjcx.lucene;

import java.util.List;

import com.yeemin.ppgjcx.repository.entity.BrandCompanyIndex;
import com.yeemin.ppgjcx.repository.lucene.BrandCompanyIndexDao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BrandCompanyIndexDaoTests {
    
    @Autowired
    private BrandCompanyIndexDao dao;

    @Test
    public void index() {
        BrandCompanyIndex brandCompanySearchInfo = new BrandCompanyIndex();
        brandCompanySearchInfo.setBrandId(1);
        brandCompanySearchInfo.setBrandName("华为手机");
        brandCompanySearchInfo.setCompanyId(1);
        brandCompanySearchInfo.setCompanyName("华为技术有限公司");
        dao.index(brandCompanySearchInfo);
    }

    @Test
    public void update() {
        BrandCompanyIndex brandCompanySearchInfo = new BrandCompanyIndex();
        brandCompanySearchInfo.setBrandId(1);
        brandCompanySearchInfo.setBrandName("华为手机");
        brandCompanySearchInfo.setCompanyId(1);
        brandCompanySearchInfo.setCompanyName("中国华为技术有限公司");
        dao.update(brandCompanySearchInfo);
    }

    @Test       
    public void queryByBrandName() {
        List<BrandCompanyIndex> list = dao.queryByBrandName("华为");
        System.out.println(list);
    }

    @Test
    public void queryByCompanyName() {
        List<BrandCompanyIndex> list = dao.queryByCompanyName("中国");
        System.out.println(list);
    }

    @Test
    public void deleteByBrandId() {
        dao.deleteByBrandId(1);
    }



}
