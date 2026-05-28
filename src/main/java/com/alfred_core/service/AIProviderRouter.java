package com.alfred_core.service;

import org.springframework.stereotype.Service;

@Service
public class AIProviderRouter {

    private final OllamaService ollamaService;

    public AIProviderRouter(OllamaService ollamaService) {
        this.ollamaService = ollamaService;
    }

    public String generate(String prompt, boolean useCloud) {

        // Gemini later

        return ollamaService.generate(prompt);
    }
}