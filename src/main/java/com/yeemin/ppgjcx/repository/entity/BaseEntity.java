package com.yeemin.ppgjcx.repository.entity;

/**
 * 数据库对应实体类基类
 * @author yeemin
 */
public abstract class BaseEntity {

    /**
     * 主键id，所有数据的主键都应该是integer类型
     */
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    
}
