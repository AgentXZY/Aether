package com.alfred_core.service;

import org.springframework.stereotype.Service;

@Service
public class QueryProcessorService {

    public String normalize(String query) {
        query = query.toLowerCase();
        query = query.replaceAll(
            "\\b(what|is|are|the|a|an|how|does|do|can|you)\\b",
            ""
        );

        return query.trim();
    }
}