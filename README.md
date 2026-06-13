# Aether

**Open-source AI Operating System for Desktop Assistance**

Alfred is the first intelligent assistant built into Aether.

---

## Vision

Aether aims to transform your computer into a **smart, private, and autonomous workspace**. It combines local AI power with modular architecture to create a true desktop AI companion.

### Key Goals
- **Local-first** design with strong privacy
- Seamless routing between local (Ollama) and cloud LLMs
- Long-term memory and conversation context
- Document understanding (PDFs, files)
- Task automation and agent capabilities
- Modular, extensible architecture

---

## Current Features

- **Chat Interface** with local LLM support (Ollama)
- **PDF Processing & RAG** (upload, extract text, chunking & storage)
- **AI Provider Router** (Local vs Cloud fallback)
- **Conversation Memory** using SQLite
- **REST API** for chat and document operations

---

## Tech Stack

### Backend
- **Java 21**
- **Spring Boot 3.3**
- **Maven**
- **Spring Data JPA + SQLite**

### AI
- **Ollama** (Local LLM inference)
- **Google Gemini** (Cloud fallback)
- **Apache PDFBox** (PDF extraction)

### Future Stack
- Tauri (Desktop UI)
- Vector Database (for better RAG)
- Voice Input/Output
- Agent Framework

---

## Project Architecture

```bash
com.alfred_core/
├── AlfredCoreApplication.java                  ← Entry point, bootstraps Spring Boot
│
├── config/
│   ├── CorsConfig.java                         ← Allows cross-origin requests from the UI
│   └── RestTemplateConfig.java                 ← Bean for outbound HTTP calls (Ollama, Gemini)
│
├── controller/                                 ← REST layer — handles all incoming HTTP requests
│   ├── AlfredChatController.java               ← Main chat endpoint (/alfred/chat)
│   ├── ChatMessageController.java              ← Fetch/delete conversation history
│   ├── PdfDocumentController.java              ← PDF upload and management
│   ├── QueryController.java                    ← Direct semantic query endpoint
│   ├── ScrapingController.java                 ← Trigger URL scraping (/scrape)
│   └── SearchController.java                   ← Web search endpoint (Tavily)
│
├── automation/web/                             ← Web intelligence module (scraping + search)
│   ├── WebSearchTool.java                      ← Facade combining search + scrape for the AI
│   ├── dto/
│   │   ├── SearchResponse.java                 ← Wrapper for search API response
│   │   └── SearchResult.java                   ← Single search result item
│   ├── ingestion/
│   │   ├── WebChunk.java                       ← Scraped page broken into embeddable chunks
│   │   └── WebIngestionService.java            ← Chunks and embeds scraped web content
│   ├── scraping/
│   │   ├── WebScraper.java                     ← Interface for scraper implementations
│   │   ├── JsoupScraper.java                   ← Jsoup-based HTML scraper (impl)
│   │   ├── ScrapingService.java                ← Orchestrates scraping via WebScraper
│   │   └── WebPage.java                        ← Model holding scraped page content
│   └── search/
│       ├── SearchProvider.java                 ← Interface for search providers
│       ├── TavilySearchProvider.java           ← Tavily API search implementation
│       └── WebSearchService.java               ← Delegates to SearchProvider impl
│
├── intent/                                     ← Local intent classification (no LLM needed)
│   ├── IntentType.java                         ← Enum of intent categories (CHAT, PDF, WEB…)
│   ├── IntentResult.java                       ← Holds classified intent + confidence score
│   ├── CosineSimilarityUtil.java               ← Vector math for similarity scoring
│   ├── MiniLMEmbeddingService.java             ← Local MiniLM embeddings for intent matching
│   ├── IntentDatasetService.java               ← Loads and embeds Intents.json at startup
│   ├── IntentRouterService.java                ← Classifies user query → IntentType
│   └── IntentController.java                   ← Debug endpoint to test intent classification
│
├── dto/                                        ← Request/response shapes crossing the API boundary
│   ├── ChatRequest.java
│   ├── ChatResponse.java
│   └── SearchResultDto.java
│
├── entity/                                     ← JPA database models (SQLite)
│   ├── ChatMessage.java                        ← Persisted chat turn (role + content)
│   ├── PdfDocument.java                        ← Metadata for an ingested PDF
│   └── PdfChunk.java                           ← Text chunk with embedding vector
│
├── repository/                                 ← Spring Data JPA — database access layer
│   ├── ChatMessageRepository.java
│   ├── PdfDocumentRepository.java
│   └── PdfChunkRepository.java
│
├── service/                                    ← Core business logic
│   ├── AlfredChatService.java                  ← Main orchestrator (intent → retrieve → generate)
│   ├── AIProviderRouter.java                   ← Switches between Ollama and Gemini
│   ├── OllamaService.java                      ← Calls local Ollama LLM
│   ├── GeminiService.java                      ← Calls Gemini cloud API (fallback)
│   ├── EmbeddingService.java                   ← Generates embeddings via Ollama nomic-embed
│   ├── SemanticSearchService.java              ← Cosine similarity search over PDF chunks
│   ├── RetrievalService.java                   ← Unified retrieval (semantic + keyword fallback)
│   ├── PdfSearchService.java                   ← Keyword-based PDF chunk search
│   ├── PdfDocumentService.java                 ← PDF ingestion pipeline (PDFBox → chunk → embed)
│   ├── PromptBuilderService.java               ← Assembles final prompt with context + history
│   ├── ConversationContextService.java         ← Fetches recent chat history for context window
│   ├── QueryProcessorService.java              ← Pre-processes and normalizes user queries
│   └── ChatMessageService.java                 ← Persists and retrieves chat messages
│
├── util/
│   └── TextChunker.java                        ← Splits large text into overlapping chunks
│
├── testcontroller/                             ← Dev/debug only — remove before production
│   ├── MiniLMTestController.java               ← Tests MiniLM embedding endpoint
│   ├── TestController1.java                    ← General scratchpad controller
│   └── TestEmbedding.java                      ← Tests embedding generation manually
│
└── resources/
    ├── application.properties                  ← Main config (DB, ports, Ollama URL)
    ├── application-secret.properties           ← API keys (Gemini, Tavily) — gitignored
    ├── Intent/
    │   └── Intents.json                        ← Training phrases for intent classification
    └── static/
        └── index.html                          ← Cyberpunk chat UI (served directly)
