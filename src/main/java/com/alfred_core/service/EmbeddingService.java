package com.alfred_core.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmbeddingService {

    private final RestTemplate restTemplate;

    public EmbeddingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @SuppressWarnings("unchecked")
    public List<Double> generateEmbedding(String text) {

        String url = "http://localhost:11434/api/embeddings";

        Map<String, Object> request = Map.of(
                "model", "nomic-embed-text",
                "prompt", text
        );

        Map<String, Object> response =
                restTemplate.postForObject(
                        url,
                        request,
                        Map.class
                );

        return (List<Double>) response.get("embedding");
    }

    public double cosineSimilarity(
            List<Double> a,
            List<Double> b) {

        if (a.size() != b.size()) {
            throw new IllegalArgumentException(
                    "Embedding dimensions do not match");
        }

        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;

        for (int i = 0; i < a.size(); i++) {

            dotProduct += a.get(i) * b.get(i);

            normA += Math.pow(a.get(i), 2);

            normB += Math.pow(b.get(i), 2);
        }

        return dotProduct /
                (Math.sqrt(normA) * Math.sqrt(normB));
    }

    public String embeddingToString(
            List<Double> embedding) {

        return embedding.toString();
    }

    public List<Double> stringToEmbedding(
            String embeddingString) {

        embeddingString =
                embeddingString
                .replace("[", "")
                .replace("]", "");

        return Arrays.stream(
                    embeddingString.split(","))
                .map(String::trim)
                .map(Double::parseDouble)
                .collect(Collectors.toList());
    }
}