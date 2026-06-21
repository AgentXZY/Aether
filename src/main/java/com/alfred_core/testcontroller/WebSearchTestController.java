package com.alfred_core.testcontroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alfred_core.automation.web.dto.SearchResponse;
import com.alfred_core.automation.web.search.WebSearchService;

@RestController
public class WebSearchTestController {

    private final WebSearchService webSearchService;

    public WebSearchTestController(
            WebSearchService webSearchService) {

        this.webSearchService = webSearchService;
    }

    @GetMapping("/test-search")
    public SearchResponse testSearch() {

        return webSearchService.search(
                "latest Java 25 features"
        );
    }
}