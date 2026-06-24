package com.alfred_core.prompt.prompt_service.intent;

import org.springframework.stereotype.Component;

import com.alfred_core.prompt.prompt_service.personality.PersonalityProfile;

@Component
public class UrlAnalysisPromptStrategy
        implements PromptStrategy {

    @Override
    public String buildPrompt(
            String question,
            String history,
            String extractedData,
            PersonalityProfile personality
    ) {

        return """
                %s

                Conversation History:
                %s

                Extracted Data:
                %s

                User asks:
                %s

                Response:
                """
                .formatted(
                        personality.systemPrompt(),
                        history,
                        extractedData,
                        question
                );
    }
}