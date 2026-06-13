package com.alfred_core.automation.scraping;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Service
public class JsoupScraper implements WebScraper {

    @Override
    public String scrape(String url) {

        try {

            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .timeout(10000)
                    .get();

            return doc.body().text();

        } catch (Exception e) {

            return "Scraping failed: " + e.getMessage();

        }
    }
}