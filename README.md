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
├── AlfredCoreApplication.java          ← Entry Point
├── 
├── config/
│   └── RestTemplateConfig.java         ← Bean for HTTP calls
├── 
├── controller/                         ← REST Layer (Entry for external calls)
│   ├── AlfredChatController.java
│   ├── PdfDocumentController.java
│   ├── ChatMessageController.java
│   ├── QueryController.java
│   └── Test controllers...
├── 
├── dto/                                ← Data Transfer Objects
│   ├── ChatRequest.java
│   ├── ChatResponse.java
│   └── SearchResultDto.java
├── 
├── entity/                             ← Database Models (JPA)
│   ├── ChatMessage.java
│   ├── PdfDocument.java
│   └── PdfChunk.java
├── 
├── repository/                         ← Database Access
│   ├── ChatMessageRepository.java
│   ├── PdfDocumentRepository.java
│   └── PdfChunkRepository.java
├── 
├── service/                            ← Core Business Logic (Heart of the app)
│   ├── AlfredChatService.java          ← Main Orchestrator
│   ├── AIProviderRouter.java
│   ├── OllamaService.java
│   ├── EmbeddingService.java
│   ├── SemanticSearchService.java
│   ├── PdfSearchService.java
│   ├── PdfDocumentService.java
│   ├── PromptBuilderService.java
│   ├── ConversationContextService.java
│   ├── QueryProcessorService.java
│   └── ChatMessageService.java
├── 
├── util/
│   └── TextChunker.java
└── resources/
    └── application.properties
