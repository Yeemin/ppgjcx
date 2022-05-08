package com.yeemin.ppgjcx.controller;

import com.yeemin.ppgjcx.domain.vo.SearchResponseVO;
import com.yeemin.ppgjcx.domain.vo.SearchResultVO;
import com.yeemin.ppgjcx.service.SearchService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SearchService searchService;

    @GetMapping(value = "search", produces = MediaType.APPLICATION_JSON_VALUE)
    public SearchResponseVO search(@RequestParam("q") String q) {
        logger.info("search q = {}", q);
        return searchService.search(q);
    }

}
