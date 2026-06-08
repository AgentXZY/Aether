package com.alfred_core.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.alfred_core.dto.ChatRequest;
import com.alfred_core.dto.ChatResponse;
import com.alfred_core.dto.SearchResultDto;

@Service
public class AlfredChatService {

    private final PromptBuilderService promptBuilderService;
    private final AIProviderRouter providerRouter;
//    private final QueryProcessorService queryProcessorService;
    private final ConversationContextService conversationContextService;
    private final ChatMessageService chatMessageService;
//    private final EmbeddingService embeddingService;
    private final RetrievalService retrievalService;

    public AlfredChatService(
            PdfSearchService pdfSearchService,
            PromptBuilderService promptBuilderService,
            AIProviderRouter providerRouter,
            SemanticSearchService semanticSearchService,
//            QueryProcessorService queryProcessorService,
            ConversationContextService conversationContextService,
            ChatMessageService chatMessageService,
            EmbeddingService embeddingService,
            RetrievalService retrievalService
    ) {
        this.promptBuilderService = promptBuilderService;
        this.providerRouter = providerRouter;
//        this.queryProcessorService = queryProcessorService;
        this.conversationContextService = conversationContextService;
        this.chatMessageService = chatMessageService;
//        this.embeddingService = embeddingService;
        this.retrievalService = retrievalService;
    }

    public ChatResponse ask(ChatRequest request) {

//    	String normalizedQuery = queryProcessorService.normalize(request.getQuestion());
    	List<SearchResultDto> chunks =
    	        retrievalService.retrieve(
    	                request.getQuestion()
    	        );

    	String historyContext = conversationContextService.getRecentHistory(6);
    	System.out.println("===== HISTORY =====");
    	System.out.println(historyContext);
    	System.out.println("===================");
    	
    	chatMessageService.saveMessage(request.getQuestion(),true);
    
    	String prompt = promptBuilderService.buildPrompt(
    		    request.getQuestion(),
    		    chunks,
    		    historyContext
    		);
    	System.out.println("===== PROMPT =====");
    	System.out.println(prompt);
    	System.out.println("==================");
    	
    	String answer;
        try {
        	answer =
                    providerRouter.generate(
                            prompt,
                            request.isUseCloud()
                    );
        } catch(Exception e) {
        	answer = "Apologies, Master Bruce. The AI provider is currently unavailable.";
        }
        
        chatMessageService.saveMessage(answer,false);

        return new ChatResponse(answer, chunks);
    }
}