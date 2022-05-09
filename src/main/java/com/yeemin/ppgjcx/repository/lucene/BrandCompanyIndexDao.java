package com.yeemin.ppgjcx.repository.lucene;

import java.util.List;

import com.yeemin.ppgjcx.core.LuceneBaseDao;
import com.yeemin.ppgjcx.repository.entity.BrandCompanyIndex;

public interface BrandCompanyIndexDao extends LuceneBaseDao {
    
    default boolean checkIdExist(Integer brandId) {
        return checkIdExist("brandId", String.valueOf(brandId));
    }

    /**
     * 索引数据
     * 
     * @param brandCompanySearchInfo 数据
     */
    void index(BrandCompanyIndex brandCompanyIndex);

    /**
     * 更新索引数据
     * 
     * @param brandCompanySearchInfo 数据
     */
    void update(BrandCompanyIndex brandCompanyIndex);

    /**
     * 根据品牌名查询
     * 
     * @param brandName 品牌名
     */
    List<BrandCompanyIndex> queryByBrandName(String brandName);

    /**
     * 根据品牌名查询
     * 
     * @param brandName 品牌名
     */
    List<BrandCompanyIndex> queryByCompanyName(String companyName);

    /**
     * 根据品牌id删除索引数据
     * 
     * @param brandId 品牌id
     */
    void deleteByBrandId(Integer brandId);

}
