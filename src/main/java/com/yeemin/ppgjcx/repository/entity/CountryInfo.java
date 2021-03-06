package com.yeemin.ppgjcx.repository.entity;

import java.io.Serializable;

import com.yeemin.ppgjcx.core.BaseEntity;

/**
 * 国家信息实体类
 * 
 * @author yeemin
 */
public class CountryInfo extends BaseEntity implements Serializable {
    
    /**
     * 国家名
     */
    private String name;

    /**
     * 国籍
     */
    private String flag;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    
}
