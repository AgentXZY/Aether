package com.alfred_core.intent;

import org.springframework.stereotype.Service;

@Service
public class IntentRouterService {

    public IntentResult route(String message) {

        String lower = message.toLowerCase();

        if(lower.contains("search")) {
            return new IntentResult(
                IntentType.WEB_SEARCH,
                message.replaceFirst("(?i)search", "").trim()
            );
        }

        if(lower.contains("create folder")
           || lower.contains("create project")) {

            return new IntentResult(
                IntentType.FILE_SYSTEM,
                message
            );
        }

        if(lower.contains("open youtube")
           || lower.contains("open chrome")) {

            return new IntentResult(
                IntentType.OPEN_APP,
                message
            );
        }

        if(lower.contains("weather")) {

            return new IntentResult(
                IntentType.WEATHER,
                message
            );
        }

        return new IntentResult(
            IntentType.RAG_CHAT,
            message
        );
    }
}