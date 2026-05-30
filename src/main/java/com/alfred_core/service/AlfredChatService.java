package com.alfred_core.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.alfred_core.dto.ChatRequest;
import com.alfred_core.dto.ChatResponse;
import com.alfred_core.dto.SearchResultDto;

@Service
public class AlfredChatService {

	private final SemanticSearchService semanticSearchService;
    private final PdfSearchService pdfSearchService;
    private final PromptBuilderService promptBuilderService;
    private final AIProviderRouter providerRouter;

    public AlfredChatService(
            PdfSearchService pdfSearchService,
            PromptBuilderService promptBuilderService,
            AIProviderRouter providerRouter,
            SemanticSearchService semanticSearchService
    ) {
        this.pdfSearchService = pdfSearchService;
        this.promptBuilderService = promptBuilderService;
        this.providerRouter = providerRouter;
        this.semanticSearchService = semanticSearchService;
    }

    public ChatResponse ask(ChatRequest request) {

    	List<SearchResultDto> chunks =
    	        semanticSearchService.search(
    	                request.getQuestion()
    	        );

        String prompt =
                promptBuilderService.buildPrompt(
                        request.getQuestion(),
                        chunks
                );

        String answer =
                providerRouter.generate(
                        prompt,
                        request.isUseCloud()
                );

        return new ChatResponse(answer, chunks);
    }
}