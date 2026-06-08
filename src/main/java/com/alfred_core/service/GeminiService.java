package com.alfred_core.service;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeminiService {

    private final RestTemplate restTemplate;
    
    @Value("${gemini.api.key}") //FOR POINTING AT APPLICATION PROPERTIES
    private String API_KEY;
    private static final String URL =
        "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=";

    public GeminiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @SuppressWarnings("unchecked")
    public String generate(String prompt) {
        try {
            String safePrompt = prompt
                .replace("\\", "\\\\")
                .replace("\"", "'")
                .replace("\n", " ")
                .replace("\r", " ");

            String body = "{\"contents\":[{\"parts\":[{\"text\":\""
                + safePrompt + "\"}]}]}";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map response = restTemplate.postForObject(
                URL + API_KEY,
                new HttpEntity<>(body, headers),
                Map.class
            );

            List candidates = (List) response.get("candidates");
            Map content = (Map)((Map) candidates.get(0)).get("content");
            List parts = (List) content.get("parts");
            return ((Map) parts.get(0)).get("text").toString();

        } catch (Exception e) {
            return "Cloud provider unavailable, Sir. " +
                   "Local engine remains at your service. Error: " + e.getMessage();
        }
    }
}