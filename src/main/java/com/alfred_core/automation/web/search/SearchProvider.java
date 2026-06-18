package com.alfred_core.automation.web.search;

import com.alfred_core.automation.web.dto.SearchResponse;

public interface SearchProvider {

	public SearchResponse search(String query);
	
}
