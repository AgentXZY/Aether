//package com.alfred_core.testcontroller;
//
//import java.util.List;
//
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.alfred_core.automation.web.ingestion.WebChunk;
//import com.alfred_core.automation.web.ingestion.WebIngestionService;
//import com.alfred_core.automation.web.scraping.ScrapingService;
//import com.alfred_core.automation.web.scraping.WebPage;
//
//@RestController
//public class WebEmbeddingTest {
//
//    private final ScrapingService scrapingService;
//    private final WebIngestionService webIngestionService;
//
//    public WebEmbeddingTest(
//            ScrapingService scrapingService,
//            WebIngestionService webIngestionService) {
//
//        this.scrapingService = scrapingService;
//        this.webIngestionService = webIngestionService;
//    }
//
//    @GetMapping("/test-web")
//    public String test() {
//
//        WebPage page =
//                scrapingService.scrape(
//                        "https://www.infoworld.com/article/3846172/jdk-25-the-new-features-in-java-25.html"
//                );
//
//        List<WebChunk> chunks =
//                webIngestionService.ingest(page);
//
//        return "Chunks: " + chunks.size();
//    }
//}


package com.alfred_core.testcontroller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alfred_core.automation.web.ingestion.WebChunk;
import com.alfred_core.automation.web.ingestion.WebIngestionService;
import com.alfred_core.automation.web.research.WebSemanticSearchService;
import com.alfred_core.automation.web.scraping.ScrapingService;
import com.alfred_core.automation.web.scraping.WebPage;

@RestController
public class WebEmbeddingTest {

    private final ScrapingService scrapingService;
    private final WebIngestionService webIngestionService;
    private final WebSemanticSearchService webSemanticSearchService;

    public WebEmbeddingTest(
            ScrapingService scrapingService,
            WebIngestionService webIngestionService,
            WebSemanticSearchService webSemanticSearchService) {

        this.scrapingService = scrapingService;
        this.webIngestionService = webIngestionService;
        this.webSemanticSearchService = webSemanticSearchService;
    }

    @GetMapping("/test-web-search")
    public String test() {

        WebPage page =
                scrapingService.scrape(
                        "https://www.infoworld.com/article/3846172/jdk-25-the-new-features-in-java-25.html"
                );

        List<WebChunk> chunks =
                webIngestionService.ingest(page);

        List<WebChunk> results =
                webSemanticSearchService.search(
                        "What AI features are in Java 25?",
                        chunks
                );

        StringBuilder response = new StringBuilder();

        response.append("Top Results:\n\n");

        for (int i = 0; i < results.size(); i++) {

            response.append("Result ")
                    .append(i + 1)
                    .append(":\n\n");

            response.append(results.get(i).getText())
                    .append("\n\n---------------------------------\n\n");
        }

        return response.toString();
    }
}