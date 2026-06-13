package com.alfred_core.automation.web.scraping;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Service
public class JsoupScraper implements WebScraper {

    @Override
    public WebPage scrape(String url) {

        try {

            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .timeout(10000)
                    .get();

            WebPage page = new WebPage();

            page.setUrl(url);
            page.setTitle(doc.title());
            page.setContent(doc.body().text());

            return page;

        } catch (Exception e) {

            throw new RuntimeException(
                    "Scraping failed: " + e.getMessage()
            );
        }
    }
}