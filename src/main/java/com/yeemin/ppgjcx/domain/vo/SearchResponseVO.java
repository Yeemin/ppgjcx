package com.yeemin.ppgjcx.domain.vo;

import java.io.Serializable;
import java.util.List;

public class SearchResponseVO implements Serializable {

    /**
     * 查询条件
     */
    private String q;

    /**
     * 结果
     */
    private List<SearchResultVO> result;

    public String getQ() {
        return q;
    }

    public List<SearchResultVO> getResult() {
        return result;
    }

    public void setResult(List<SearchResultVO> result) {
        this.result = result;
    }

    public void setQ(String q) {
        this.q = q;
    }
    
}
