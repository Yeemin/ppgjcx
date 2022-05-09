package com.yeemin.ppgjcx.repository.entity;

import java.io.Serializable;

import com.yeemin.ppgjcx.core.BaseEntity;

/**
 * 类别实体类
 * 
 * @author yeemin
 */
public class CategoryInfo extends BaseEntity implements Serializable {
    
    /**
     * 类别名称
     */
    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
