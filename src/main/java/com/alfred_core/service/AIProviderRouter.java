package com.alfred_core.service;

import org.springframework.stereotype.Service;

@Service
public class AIProviderRouter {

    private final OllamaService ollamaService;
    private final GeminiService geminiService;

    public AIProviderRouter(OllamaService ollamaService,GeminiService geminiService) {
        this.ollamaService = ollamaService;
        this.geminiService = geminiService;
    }

    public String generate(String prompt, boolean useCloud) {
    	if(useCloud) return geminiService.generate(prompt);
    	return ollamaService.generate(prompt);
    }
}