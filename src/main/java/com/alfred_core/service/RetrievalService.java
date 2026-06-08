package com.alfred_core.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.alfred_core.dto.SearchResultDto;

@Service
public class RetrievalService {

    private final SemanticSearchService semanticSearchService;
    private final PdfSearchService pdfSearchService;

    public RetrievalService(
            SemanticSearchService semanticSearchService,
            PdfSearchService pdfSearchService) {

        this.semanticSearchService = semanticSearchService;
        this.pdfSearchService = pdfSearchService;
    }

    public List<SearchResultDto> retrieve(String query) {

        try {

            List<SearchResultDto> results =
                    semanticSearchService.search(query);

            if (!results.isEmpty()) {
                return results;
            }

        } catch (Exception e) {

            System.out.println(
                    "Semantic search failed. Falling back."
            );
        }

        return pdfSearchService.search(query);
    }
}
