package com.alfred_core.dto;

import java.util.List;

public class ChatResponse {

    private String answer;
    private List<SearchResultDto> sourceChunks;

    public ChatResponse(String answer, List<SearchResultDto> sourceChunks) {
        this.answer = answer;
        this.sourceChunks = sourceChunks;
    }

    public String getAnswer() {
        return answer;
    }

    public List<SearchResultDto> getSourceChunks() {
        return sourceChunks;
    }
}