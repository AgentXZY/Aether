package com.alfred_core.prompt.prompt_service.intent;

import com.alfred_core.prompt.prompt_service.personality.PersonalityProfile;

public interface PromptStrategy {

    String buildPrompt(
            String question,
            String history,
            String context,
            PersonalityProfile personality
    );
}