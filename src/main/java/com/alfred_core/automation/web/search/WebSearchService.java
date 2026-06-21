package com.alfred_core.automation.web.search;

import org.springframework.stereotype.Service;

import com.alfred_core.automation.web.dto.SearchResponse;

@Service
public class WebSearchService {

    private final SearchProvider provider;

    public WebSearchService(
            SearchProvider provider) {

        this.provider = provider;
    }

    public SearchResponse search(String query) {

        return provider.search(query);

    }
}