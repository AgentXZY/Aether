package com.alfred_core.intent;

public class IntentResult {

    private IntentType intent;
    private String extractedQuery;

    public IntentResult(IntentType intent, String extractedQuery) {
        this.intent = intent;
        this.extractedQuery = extractedQuery;
    }

    public IntentType getIntent() {
        return intent;
    }

    public String getExtractedQuery() {
        return extractedQuery;
    }
}