package com.alfred_core.automation.web.ingestion;

import java.util.List;

public class WebChunk {

	private String sourceUrl;
	private String text;
	private List<Double> embedding;

	public WebChunk() {
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public String getText() {
		return text;
	}

	public List<Double> getEmbedding() {
		return embedding;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setEmbedding(List<Double> embedding) {
		this.embedding = embedding;
	}

}