package com.yeemin.ppgjcx.repository.lucene.impl;

import java.util.List;
import java.util.Optional;

import com.yeemin.ppgjcx.core.AbstractLuceneBaseDao;
import com.yeemin.ppgjcx.repository.entity.BrandCompanyIndex;
import com.yeemin.ppgjcx.repository.lucene.BrandCompanyIndexDao;

import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.springframework.stereotype.Repository;

@Repository
public class BrandCompanyIndexDaoImpl extends AbstractLuceneBaseDao implements BrandCompanyIndexDao {

    /**
     * 索引数据
     * 
     * @param brandCompanySearchInfo 数据
     */
    public void index(BrandCompanyIndex brandCompanySearchInfo) {
        index(doc -> {
            doc.add(new StringField("brandId", String.valueOf(brandCompanySearchInfo.getBrandId()), Store.YES));
            doc.add(new TextField("brandName", brandCompanySearchInfo.getBrandName(), Store.YES));
            doc.add(new StringField("companyId", String.valueOf(brandCompanySearchInfo.getCompanyId()), Store.YES));
            doc.add(new TextField("companyName", brandCompanySearchInfo.getCompanyName(), Store.YES));
        });
    }

    /**
     * 更新索引数据
     * 
     * @param brandCompanySearchInfo 数据
     */
    public void update(BrandCompanyIndex brandCompanySearchInfo) {
        updateById("brandId", String.valueOf(brandCompanySearchInfo.getBrandId()),
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
    public List<BrandCompanyIndex> queryByBrandName(String brandName) {
        return queryListBySingleField("brandName", brandName,
                document -> {
                    BrandCompanyIndex info = new BrandCompanyIndex();
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
    public List<BrandCompanyIndex> queryByCompanyName(String companyName) {
        return queryListBySingleField("companyName", companyName,
                document -> {
                    BrandCompanyIndex info = new BrandCompanyIndex();
                    info.setBrandId(Integer.parseInt(document.get("brandId")));
                    info.setBrandName(document.get("brandName"));
                    info.setCompanyId(Integer.parseInt(document.get("companyId")));
                    info.setCompanyName(document.get("companyName"));
                    return info;
                });
    }

    /**
     * 根据品牌id删除索引数据
     * 
     * @param brandId 品牌id
     */
    public void deleteByBrandId(Integer brandId) {
        deleteById("brandId", String.valueOf(brandId));
    }

    @Override
    protected String getDic() {
        return "brandCompany";
    }

}
