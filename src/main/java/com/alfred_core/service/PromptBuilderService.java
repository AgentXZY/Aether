package com.alfred_core.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.alfred_core.dto.SearchResultDto;

@Service
public class PromptBuilderService {

    public String buildPrompt(
            String question,
            List<SearchResultDto> chunks
    ) {

        StringBuilder prompt = new StringBuilder();

        prompt.append("""
            You are Alfred, an intelligent AI assistant.

            Answer ONLY from the provided context.

            If the answer is not in the context,
            say you could not find relevant information.

            CONTEXT:
            """);

        for (SearchResultDto chunk : chunks) {

            prompt.append("\n");

            prompt.append(chunk.getChunkText());

            prompt.append("\n");
        }

        prompt.append("\nUSER QUESTION:\n");

        prompt.append(question);

        prompt.append("\n\nANSWER:");

        return prompt.toString();
    }
}