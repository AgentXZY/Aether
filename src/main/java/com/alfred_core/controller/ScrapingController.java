package com.alfred_core.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alfred_core.automation.web.scraping.ScrapingService;
import com.alfred_core.automation.web.scraping.WebPage;

@RestController
@RequestMapping("/scrape")
public class ScrapingController {

    private final ScrapingService scrapingService;

    public ScrapingController(
            ScrapingService scrapingService) {

        this.scrapingService = scrapingService;
    }

    @GetMapping
    public WebPage scrape(
            @RequestParam String url) {

        return scrapingService.scrape(url);
    }
}