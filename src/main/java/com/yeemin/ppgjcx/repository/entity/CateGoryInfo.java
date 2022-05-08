package com.yeemin.ppgjcx.repository.entity;

import java.io.Serializable;

import com.yeemin.ppgjcx.core.BaseEntity;

/**
 * 类别信息实体类
 * 
 * @author yeemin
 */
public class CateGoryInfo extends BaseEntity implements Serializable {
    
    /**
     * 类别名称
     */
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
