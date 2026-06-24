package com.alfred_core.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.alfred_core.automation.web.dto.SearchResponse;
import com.alfred_core.automation.web.search.WebSearchService;
import com.alfred_core.dto.ChatRequest;
import com.alfred_core.dto.ChatResponse;
import com.alfred_core.dto.SearchResultDto;
import com.alfred_core.intent.IntentResult;
import com.alfred_core.intent.IntentRouterService;
import com.alfred_core.intent.IntentType;
import com.alfred_core.prompt.prompt_service.PromptOrchestrator;

@Service
public class AlfredChatService {

//    private final PromptBuilderService promptBuilderService;
	private final PromptOrchestrator promptOrchestrator;
    private final AIProviderRouter providerRouter;
//    private final QueryProcessorService queryProcessorService;
    private final ConversationContextService conversationContextService;
    private final ChatMessageService chatMessageService;
//    private final EmbeddingService embeddingService;
    private final RetrievalService retrievalService;
    private final IntentRouterService intentRouter;
    private final WebSearchService webSearchService;
//    private final ScrapingService scrapingService;
    private final UrlSearchService urlSearchService;

    public AlfredChatService(
    		PromptOrchestrator promptOrchestrator,
            PdfSearchService pdfSearchService,
//            PromptBuilderService promptBuilderService,
            AIProviderRouter providerRouter,
            SemanticSearchService semanticSearchService,
//            QueryProcessorService queryProcessorService,
            ConversationContextService conversationContextService,
            ChatMessageService chatMessageService,
            EmbeddingService embeddingService,
            RetrievalService retrievalService,
            IntentRouterService intentRouter,
            WebSearchService webSearchService,
//            ScrapingService scrapingService,
            UrlSearchService urlSearchService
    ) {
//        this.promptBuilderService = promptBuilderService;
    	this.promptOrchestrator = promptOrchestrator;
        this.providerRouter = providerRouter;
//        this.queryProcessorService = queryProcessorService;
        this.conversationContextService = conversationContextService;
        this.chatMessageService = chatMessageService;
//        this.embeddingService = embeddingService;
        this.retrievalService = retrievalService;
        this.intentRouter = intentRouter;
        this.webSearchService = webSearchService;
//        this.scrapingService = scrapingService;
        this.urlSearchService = urlSearchService;
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

        case WEB_SEARCH -> {
        	String query = request.getQuestion();
            
            if (UrlSearchService.hasUrls(query)) {
                
            	List<String> urls = urlSearchService.extractUrls(query);
            	String extractedData = urlSearchService.generatePrompt(urls);

            	String prompt =
            	        promptOrchestrator.buildPrompt(
            	                IntentType.WEB_SEARCH,
            	                true,
            	                query,
            	                historyContext,
            	                extractedData
            	        );

                answer = providerRouter.generate(prompt, request.isUseCloud());

            } else {
                // Normal search flow
                SearchResponse response = webSearchService.search(query);

                StringBuilder webContext = new StringBuilder();
                response.getResults().forEach(result ->
                        webContext.append(result.getTitle())
                                  .append("\n")
                                  .append(result.getContent())
                                  .append("\n\n")
                );

                String prompt =
                        promptOrchestrator.buildPrompt(
                                IntentType.WEB_SEARCH,
                                false,
                                query,
                                historyContext,
                                webContext.toString()
                        );

                answer = providerRouter.generate(prompt, request.isUseCloud());
            }
        }


        case OPEN_APP ->
            answer = "App launching capability incoming, Sir.";

        case MEMORY_STORE ->
            answer = "Noted, Sir. I shall keep that in memory.";

        case MEMORY_RECALL ->
            answer = "Let me check my records for that, Sir. Full memory retrieval coming very soon.";

        case WEATHER ->
            answer = "Weather service not yet connected, Sir. Working on it.";
            
            default -> {

                chunks = retrievalService.retrieve(
                        request.getQuestion()
                );

                String chunkContext =
                        chunks.stream()
                              .map(SearchResultDto::getChunkText)
                              .reduce("", (a, b) -> a + "\n\n" + b);

                String prompt =
                        promptOrchestrator.buildPrompt(
                                IntentType.RAG_CHAT,
                                false,
                                request.getQuestion(),
                                historyContext,
                                chunkContext
                        );

                try {

                    answer = providerRouter.generate(
                            prompt,
                            request.isUseCloud()
                    );

                } catch (Exception e) {

                    answer =
                        "Apologies, Sir. The AI provider is currently unavailable.";
                }
            }
       };
       chatMessageService.saveMessage(answer,false);
       		return new ChatResponse(answer, chunks);
    }
}