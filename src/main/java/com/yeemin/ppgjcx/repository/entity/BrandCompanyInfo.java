package com.yeemin.ppgjcx.repository.entity;

import java.io.Serializable;

import com.yeemin.ppgjcx.core.BaseEntity;

public class BrandCompanyInfo extends BaseEntity implements Serializable {

    private Integer brandId;

    private Integer companyId;
    
    private Integer percent;

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getPercent() {
        return percent;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }

    
    
}
