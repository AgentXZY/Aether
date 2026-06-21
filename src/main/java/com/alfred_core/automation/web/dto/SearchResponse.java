package com.alfred_core.automation.web.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResponse {

	private String query;
	private List<SearchResult> results;

	public String getQuery() {
		return query;
	}

	public List<SearchResult> getResults() {
		return results;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public void setResults(List<SearchResult> results) {
		this.results = results;
	}

}