package com.alfred_core.automation.web.search;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alfred_core.automation.web.dto.SearchResponse;

@Service
public class TavilySearchProvider implements SearchProvider {

    private final RestTemplate restTemplate;

    @Value("${tavily.api.key}")
    private String apiKey;

    public TavilySearchProvider(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public SearchResponse search(String query) {

        String url = "https://api.tavily.com/search";

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = Map.of(
                "api_key", apiKey,
                "query", query,
                "search_depth", "basic",
                "max_results", 5
        );

        HttpEntity<Map<String, Object>> entity =
                new HttpEntity<>(body, headers);

        ResponseEntity<SearchResponse> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.POST,
                        entity,
                        SearchResponse.class
                );

        System.out.println(response.getBody());
        return response.getBody();
    }
}