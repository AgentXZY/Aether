package com.alfred_core.prompt.prompt_service.intent;

import org.springframework.stereotype.Component;

import com.alfred_core.prompt.prompt_service.personality.PersonalityProfile;

@Component
public class RagPromptStrategy
        implements PromptStrategy {

    @Override
    public String buildPrompt(
            String question,
            String history,
            String chunkText,
            PersonalityProfile personality
    ) {

        return """
                %s

                Answer using the supplied context.

                Conversation History:
                %s

                Context:
                %s

                User Question:
                %s

                Response:
                """
                .formatted(
                        personality.systemPrompt(),
                        history,
                        chunkText,
                        question
                );
    }
}