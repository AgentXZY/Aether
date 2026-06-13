package com.alfred_core.automation.web;

import org.springframework.stereotype.Service;

@Service
public class WebSearchService {

    private final SearchProvider provider;

    public WebSearchService(
            SearchProvider provider) {

        this.provider = provider;
    }

    public String search(String query) {

        return provider.search(query);

    }
}