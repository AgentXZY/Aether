package com.alfred_core.intent;

import java.util.List;
import java.util.Map;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MiniLMEmbeddingService {

    private final RestTemplate restTemplate;

    public MiniLMEmbeddingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @SuppressWarnings("unchecked")
    public List<Double> embed(String text) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = Map.of(
                "model", "all-minilm:33m",
                "prompt", text
        );

        Map<String, Object> response =
                restTemplate.postForObject(
                        "http://localhost:11434/api/embeddings",
                        new HttpEntity<>(body, headers),
                        Map.class
                );

        return (List<Double>) response.get("embedding");
    }
}