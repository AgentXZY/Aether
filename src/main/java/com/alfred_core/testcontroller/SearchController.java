package com.alfred_core.testcontroller;

import org.springframework.web.bind.annotation.*;

import com.alfred_core.automation.web.WebSearchService;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final WebSearchService webSearchService;

    public SearchController(
            WebSearchService webSearchService) {

        this.webSearchService = webSearchService;
    }

    @GetMapping
    public String search(
            @RequestParam String q) {

        return webSearchService.search(q);
    }
}