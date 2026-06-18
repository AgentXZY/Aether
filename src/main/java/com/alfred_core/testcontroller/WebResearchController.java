package com.alfred_core.testcontroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alfred_core.automation.web.scraping.JinaScraper;
import com.alfred_core.automation.web.scraping.WebPage;

@RestController
public class WebResearchController {

    @GetMapping("/research/jina")
    public String testJinaScraping() {

        JinaScraper scraper =
                new JinaScraper(new RestTemplate());

        WebPage page =
                scraper.scrape(
                        "https://www.fifa.com/"
                );

        return page.getContent();
    }
}