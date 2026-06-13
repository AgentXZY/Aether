package com.alfred_core.automation.scraping;

import org.springframework.stereotype.Service;

@Service
public class ScrapingService {

    private final WebScraper scraper;

    public ScrapingService(WebScraper scraper) {
        this.scraper = scraper;
    }

    public String scrape(String url) {
        return scraper.scrape(url);
    }
}