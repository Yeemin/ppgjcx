package com.yeemin.ppgjcx.repository.entity;

import java.io.Serializable;

public class BrandCompanyIndex implements Serializable {

    private Integer brandId;

    private String brandName;

    private Integer companyId;

    private String companyName;

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return "BrandCompanySO [brandId=" + brandId + ", brandName=" + brandName + ", companyId=" + companyId
                + ", companyName=" + companyName + "]";
    }

}
