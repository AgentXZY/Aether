package com.alfred_core.prompt.prompt_service.intent;

import org.springframework.stereotype.Component;

import com.alfred_core.prompt.prompt_service.personality.PersonalityProfile;

@Component
public class WebSearchPromptStrategy
        implements PromptStrategy {

    @Override
    public String buildPrompt(
            String question,
            String history,
            String webResults,
            PersonalityProfile personality
    ) {

        return """
                %s

                Conversation History:
                %s

                Web Search Results:
                %s

                User asks:
                %s

                Response:
                """
                .formatted(
                        personality.systemPrompt(),
                        history,
                        webResults,
                        question
                );
    }
}