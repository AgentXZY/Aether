package com.alfred_core.dto;

public class SearchResultDto {

    private Long documentId;
    private String chunkText;

    public SearchResultDto(Long documentId, String chunkText) {
        this.documentId = documentId;
        this.chunkText = chunkText;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public String getChunkText() {
        return chunkText;
    }
}