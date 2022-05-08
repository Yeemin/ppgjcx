package com.yeemin.ppgjcx.service;

import com.yeemin.ppgjcx.domain.vo.SearchResponseVO;
import com.yeemin.ppgjcx.domain.vo.SearchResultVO;

public interface SearchService {
    
    SearchResponseVO search(String q);

}
