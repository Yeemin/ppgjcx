package com.yeemin.ppgjcx.repository.entity;

import java.io.Serializable;

/**
 * 公司国家信息实体类
 * 
 * @author yeemin
 */
public class CompanyCountryInfo implements Serializable {
    
    /**
     * 公司id
     */
    private Integer companyId;

    /**
     * 国家id
     */
    private Integer countryId;

    /**
     * 占比，1-100
     */
    private Integer percent;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public Integer getPercent() {
        return percent;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }

    

}
