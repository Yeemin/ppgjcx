package com.yeemin.ppgjcx.repository.lucene;

import java.util.List;
import java.util.Optional;

import com.yeemin.ppgjcx.core.LuceneOperation;
import com.yeemin.ppgjcx.repository.entity.BrandCompanySearchInfo;

import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.springframework.stereotype.Repository;

@Repository
public class BrandCompanySearchQueryer extends LuceneOperation {

    public long countByBrandId(Integer brandId) {
        return countByTermQuery("brandId", String.valueOf(brandId));
    }

    /**
     * 索引数据
     * 
     * @param brandCompanySearchInfo 数据
     */
    public void index(BrandCompanySearchInfo brandCompanySearchInfo) {
        index(doc -> {
            doc.add(new StringField("brandId", String.valueOf(brandCompanySearchInfo.getBrandId()), Store.YES));
            doc.add(new TextField("brandName", brandCompanySearchInfo.getBrandName(), Store.YES));
            doc.add(new StringField("companyId", String.valueOf(brandCompanySearchInfo.getCompanyId()), Store.YES));
            doc.add(new TextField("companyName", brandCompanySearchInfo.getCompanyName(), Store.YES));
        });
    }

    public void update(BrandCompanySearchInfo brandCompanySearchInfo) {
        update("brandId", String.valueOf(brandCompanySearchInfo.getBrandId()),
                doc -> {
                    Optional.ofNullable(brandCompanySearchInfo.getBrandId()).map(String::valueOf)
                            .ifPresent(value -> doc.add(new StringField("brandId", value, Store.YES)));
                    Optional.ofNullable(brandCompanySearchInfo.getBrandName())
                            .ifPresent(value -> doc.add(new TextField("brandName", value, Store.YES)));
                    Optional.ofNullable(brandCompanySearchInfo.getCompanyId()).map(String::valueOf)
                            .ifPresent(value -> doc.add(new StringField("companyId", value, Store.YES)));
                    Optional.ofNullable(brandCompanySearchInfo.getCompanyName())
                            .ifPresent(value -> doc.add(new TextField("companyName", value, Store.YES)));
                });
    }

    /**
     * 根据品牌名查询
     * 
     * @param brandName 品牌名
     */
    public List<BrandCompanySearchInfo> queryByBrandName(String brandName) {
        return queryParser("brandName", brandName,
                document -> {
                    BrandCompanySearchInfo info = new BrandCompanySearchInfo();
                    info.setBrandId(Integer.parseInt(document.get("brandId")));
                    info.setBrandName(document.get("brandName"));
                    info.setCompanyId(Integer.parseInt(document.get("companyId")));
                    info.setCompanyName(document.get("companyName"));
                    return info;
                });
    }

    /**
     * 根据品牌名查询
     * 
     * @param brandName 品牌名
     */
    public List<BrandCompanySearchInfo> queryByCompanyName(String companyName) {
        return queryParser("companyName", companyName,
                document -> {
                    BrandCompanySearchInfo info = new BrandCompanySearchInfo();
                    info.setBrandId(Integer.parseInt(document.get("brandId")));
                    info.setBrandName(document.get("brandName"));
                    info.setCompanyId(Integer.parseInt(document.get("companyId")));
                    info.setCompanyName(document.get("companyName"));
                    return info;
                });
    }

    public void deleteByBrandId(Integer brandId) {
        deleteByQuery("brandId", String.valueOf(brandId));
    }

}
