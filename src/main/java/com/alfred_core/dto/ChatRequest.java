package com.alfred_core.dto;

public class ChatRequest {

    private String question;
    private boolean useCloud;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public boolean isUseCloud() {
        return useCloud;
    }

    public void setUseCloud(boolean useCloud) {
        this.useCloud = useCloud;
    }
}