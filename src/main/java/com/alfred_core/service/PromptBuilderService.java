package com.alfred_core.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.alfred_core.dto.SearchResultDto;

@Service
public class PromptBuilderService {

	public String buildPrompt(
	        String question,
	        List<SearchResultDto> chunks,
	        String historyContext
	) {

	    StringBuilder contextBuilder = new StringBuilder();

	    for (SearchResultDto chunk : chunks) {
	        contextBuilder
	                .append(chunk.getChunkText())
	                .append("\n\n");
	    }

	    return """
	    		You are Alfred Pennyworth, elite AI tactical assistant.

	    		Address the user respectfully as Sir.
	    		Never assume the user's real name.
	    		If the user's name appears in conversation history, use that information.

	    		INSTRUCTIONS:
	    		- Prioritize conversation history for personal facts.
	    		- Prioritize document context for document questions.
	    		- If history contains a fact about the user, trust it.
	    		- Be concise and accurate.

	    		CONVERSATION HISTORY:
	    		%s

	    		DOCUMENT CONTEXT:
	    		%s

	    		User asks:
	    		%s

	    		Alfred responds:
	    		"""
	    		.formatted(
	    		    historyContext,
	    		    contextBuilder.toString(),
	    		    question
	    		);
	}
}