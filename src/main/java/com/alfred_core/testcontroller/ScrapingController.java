package com.alfred_core.testcontroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alfred_core.automation.scraping.ScrapingService;

@RestController
@RequestMapping("/scrape")
public class ScrapingController {

    private final ScrapingService scrapingService;

    public ScrapingController(
            ScrapingService scrapingService) {

        this.scrapingService = scrapingService;
    }

    @GetMapping
    public String scrape(
            @RequestParam String url) {

        return scrapingService.scrape(url);
    }
}