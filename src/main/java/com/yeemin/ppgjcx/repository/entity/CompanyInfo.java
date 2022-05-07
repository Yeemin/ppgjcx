package com.yeemin.ppgjcx.repository.entity;

import java.io.Serializable;

/**
 * 公司信息实体类
 * 
 * @author yeemin
 */
public class CompanyInfo implements Serializable {
    
    /**
     * 公司信息
     */
    private String name;

    /**
     * 公司logo
     */
    private String logo;

    public String getName() {
        return name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public void setName(String name) {
        this.name = name;
    }

}
