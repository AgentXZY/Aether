package com.alfred_core.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.alfred_core.dto.ChatRequest;
import com.alfred_core.dto.ChatResponse;
import com.alfred_core.dto.SearchResultDto;
import com.alfred_core.intent.IntentResult;
import com.alfred_core.intent.IntentRouterService;
import com.alfred_core.intent.IntentType;

@Service
public class AlfredChatService {

    private final PromptBuilderService promptBuilderService;
    private final AIProviderRouter providerRouter;
//    private final QueryProcessorService queryProcessorService;
    private final ConversationContextService conversationContextService;
    private final ChatMessageService chatMessageService;
//    private final EmbeddingService embeddingService;
    private final RetrievalService retrievalService;
    private final IntentRouterService intentRouter;

    public AlfredChatService(
            PdfSearchService pdfSearchService,
            PromptBuilderService promptBuilderService,
            AIProviderRouter providerRouter,
            SemanticSearchService semanticSearchService,
//            QueryProcessorService queryProcessorService,
            ConversationContextService conversationContextService,
            ChatMessageService chatMessageService,
            EmbeddingService embeddingService,
            RetrievalService retrievalService,
            IntentRouterService intentRouter
    ) {
        this.promptBuilderService = promptBuilderService;
        this.providerRouter = providerRouter;
//        this.queryProcessorService = queryProcessorService;
        this.conversationContextService = conversationContextService;
        this.chatMessageService = chatMessageService;
//        this.embeddingService = embeddingService;
        this.retrievalService = retrievalService;
        this.intentRouter = intentRouter;
    }

    public ChatResponse ask(ChatRequest request) {

//    	String normalizedQuery = queryProcessorService.normalize(request.getQuestion());

    	IntentResult intentResult = intentRouter.route(request.getQuestion());
    	IntentType intent = intentResult.getIntent();
    	
    	System.out.println("===== INTENT: " + intent + " =====");
    	
    	String historyContext = conversationContextService.getRecentHistory(6);
    	
    	System.out.println("===== HISTORY =====");
    	System.out.println(historyContext);
    	System.out.println("===================");
    	
    	chatMessageService.saveMessage(request.getQuestion(),true);
    	
    	String answer;
    	List<SearchResultDto> chunks = List.of();
    	
    	switch (intent) {

        case FILE_SYSTEM ->
            answer = "File system capability coming soon, Sir. I am being equipped for that as we speak.";

        case WEB_SEARCH ->
            answer = "Web search integration is being prepared, Sir. Stand by.";

        case OPEN_APP ->
            answer = "App launching capability incoming, Sir.";

        case MEMORY_STORE ->
            answer = "Noted, Sir. I shall keep that in memory.";

        case MEMORY_RECALL ->
            answer = "Let me check my records for that, Sir. Full memory retrieval coming very soon.";

        case WEATHER ->
            answer = "Weather service not yet connected, Sir. Working on it.";
            
        default ->{
        	chunks =
        	        retrievalService.retrieve(
        	                request.getQuestion()
        	        );
        	
        	String prompt = promptBuilderService.buildPrompt(
        		    request.getQuestion(),
        		    chunks,
        		    historyContext
        		);
        	
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
        	}
       };
       		return new ChatResponse(answer, chunks);
    }
}