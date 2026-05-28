package com.alfred_core.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.alfred_core.entity.PdfChunk;
import com.alfred_core.service.PdfSearchService;

@RestController
@RequestMapping("/engine")
public class QueryController {
    private final PdfSearchService searchService;
    
    public QueryController(PdfSearchService searchService) {
        this.searchService = searchService;
    }
    
    @GetMapping("/search")
    public List<PdfChunk> search(@RequestParam String q) {
        return searchService.search(q);
    }
}