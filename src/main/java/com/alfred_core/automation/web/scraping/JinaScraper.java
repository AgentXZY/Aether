package com.alfred_core.automation.web.scraping;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Primary
public class JinaScraper implements WebScraper {

    private final RestTemplate restTemplate;

    public JinaScraper(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public WebPage scrape(String url) {

        try {

            String jinaUrl =
                    "https://r.jina.ai/http://"
                    + url.replace("https://", "")
                         .replace("http://", "");

            String content =
                    restTemplate.getForObject(
                            jinaUrl,
                            String.class
                    );

            WebPage page = new WebPage();

            page.setUrl(url);
            page.setTitle("Jina Extracted");
            page.setContent(content);

            return page;

        } catch (Exception e) {

            throw new RuntimeException(
                    "Jina scraping failed: "
                    + e.getMessage()
            );
        }
    }
}