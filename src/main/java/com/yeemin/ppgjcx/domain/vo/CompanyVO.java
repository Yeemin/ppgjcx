package com.yeemin.ppgjcx.domain.vo;

import java.io.Serializable;
import java.util.List;

public class CompanyVO implements Serializable {

    /**
     * 公司名
     */
    private String name;

    /**
     * 公司logo
     */
    private String logo;
    
    /**
     * 国籍信息
     */
    private List<CountryVO> country;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public List<CountryVO> getCountry() {
        return country;
    }

    public void setCountry(List<CountryVO> country) {
        this.country = country;
    }

    
}
