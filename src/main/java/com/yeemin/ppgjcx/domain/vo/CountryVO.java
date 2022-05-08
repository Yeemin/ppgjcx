package com.yeemin.ppgjcx.domain.vo;

import java.io.Serializable;

public class CountryVO implements Serializable{
    
    /**
     * 国籍名
     */
    private String name;

    /**
     * 国旗
     */
    private String flag;

    /**
     * 资产占比
     */
    private Integer percent;

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

    public Integer getPercent() {
        return percent;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }

    

}
