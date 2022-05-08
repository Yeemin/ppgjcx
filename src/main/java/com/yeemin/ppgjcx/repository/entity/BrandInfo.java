package com.yeemin.ppgjcx.repository.entity;

import java.io.Serializable;

import com.yeemin.ppgjcx.core.BaseEntity;

/**
 * 品牌信息实体类
 * 
 * @author yeemin
 */
public class BrandInfo extends BaseEntity implements Serializable {
    
    /**
     * 品牌名称
     */
    private String name;

    /**
     * 公司id
     */
    private Integer companyId;

    /**
     * 类别id
     */
    private Integer categoryId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
    

}
