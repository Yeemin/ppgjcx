package com.yeemin.ppgjcx.domain.vo;

import java.io.Serializable;
import java.util.List;

public class SearchResultVO implements Serializable {
    
    /**
     * 品牌名
     */
    private String brand;

    /**
     * 分类
     */
    private List<String> category;

    /**
     * 国籍
     */
    private List<CountryVO> country;

    /**
     * 公司
     */
    private List<CompanyVO> company;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public List<CountryVO> getCountry() {
        return country;
    }

    public void setCountry(List<CountryVO> country) {
        this.country = country;
    }

    public List<CompanyVO> getCompany() {
        return company;
    }

    public void setCompany(List<CompanyVO> company) {
        this.company = company;
    }

    

}
