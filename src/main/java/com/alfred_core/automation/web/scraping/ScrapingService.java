package com.alfred_core.automation.web.scraping;

import org.springframework.stereotype.Service;

@Service
public class ScrapingService {

    private final WebScraper scraper;

    public ScrapingService(WebScraper scraper) {
        this.scraper = scraper;
    }

    public WebPage scrape(String url) {
        return scraper.scrape(url);
    }
}