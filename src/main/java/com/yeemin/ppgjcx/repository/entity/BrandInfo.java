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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
