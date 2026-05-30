package com.alfred_core.testcontroller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alfred_core.service.EmbeddingService;

@RestController
public class TestEmbedding {

    private final EmbeddingService embeddingService;

    public TestEmbedding(EmbeddingService embeddingService) {
        this.embeddingService = embeddingService;
    }

    @GetMapping("/test-embedding")
    public String testEmbedding() {

        List<Double> embedding =
                embeddingService.generateEmbedding(
                        "What is polymorphism?"
                );

        return "Embedding generated successfully. Vector size = "
                + embedding.size();
    }
}