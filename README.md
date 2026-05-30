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
src/main/java/com/alfred_core/
├── AlfredCoreApplication.java
├── config/              # Configuration classes & beans
├── controller/          # REST API endpoints
├── dto/                 # Data Transfer Objects
├── entity/              # JPA Entities (PdfDocument, ChatMessage, etc.)
├── repository/          # Data Access Layer
├── service/             # Business Logic
│   ├── OllamaService
│   ├── AlfredChatService
│   ├── PdfDocumentService
│   ├── AIProviderRouter
│   └── ...
└── util/                # Helper classes (TextChunker, etc.)
