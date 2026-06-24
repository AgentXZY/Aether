package com.alfred_core.prompt.prompt_service;

import org.springframework.stereotype.Service;
import com.alfred_core.intent.IntentType;
import com.alfred_core.prompt.prompt_service.factory.PromptStrategyFactory;
import com.alfred_core.prompt.prompt_service.personality.PersonalityProfile;

@Service
public class PromptOrchestrator {

    private final PersonalityProfile personality;
    private final PromptStrategyFactory strategyFactory;

    public PromptOrchestrator(
            PersonalityProfile personality,
            PromptStrategyFactory strategyFactory
    ) {
        this.personality = personality;
        this.strategyFactory = strategyFactory;
    }

    public String buildPrompt(
            IntentType intent,
            boolean containsUrl,
            String question,
            String history,
            String context
    ) {

        return strategyFactory
                .get(intent, containsUrl)
                .buildPrompt(
                        question,
                        history,
                        context,
                        personality
                );
    }
}