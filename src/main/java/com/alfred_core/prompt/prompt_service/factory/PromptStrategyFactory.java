package com.alfred_core.prompt.prompt_service.factory;

import org.springframework.stereotype.Service;

import com.alfred_core.intent.IntentType;
import com.alfred_core.prompt.prompt_service.intent.PromptStrategy;
import com.alfred_core.prompt.prompt_service.intent.RagPromptStrategy;
import com.alfred_core.prompt.prompt_service.intent.UrlAnalysisPromptStrategy;
import com.alfred_core.prompt.prompt_service.intent.WebSearchPromptStrategy;

@Service
public class PromptStrategyFactory {

    private final RagPromptStrategy rag;
    private final WebSearchPromptStrategy web;
    private final UrlAnalysisPromptStrategy url;

    public PromptStrategyFactory(
            RagPromptStrategy rag,
            WebSearchPromptStrategy web,
            UrlAnalysisPromptStrategy url
    ) {
        this.rag = rag;
        this.web = web;
        this.url = url;
    }

    public PromptStrategy get(IntentType intent,
                              boolean containsUrl) {

        if (containsUrl)
            return url;

        return switch(intent) {

            case WEB_SEARCH -> web;

            default -> rag;
        };
    }
}