# Aether

Open-source AI operating system for desktop assistance.

Alfred is the first built-in assistant.

---

## Vision

Aether is designed to turn your computer into an intelligent workspace capable of:

- Understanding context
- Remembering conversations
- Reading files and emails
- Executing tasks
- Routing between local and cloud AI
- Acting like a real desktop assistant

This project focuses on:
- local-first AI
- modular architecture
- privacy
- automation
- autonomous agents

---

## Current Stack

### Backend
- Java
- Spring Boot
- Maven

### AI
- Ollama
- Gemini API
- Local LLM routing

### Future
- Tauri desktop app
- Voice interaction
- Agent framework
- Long-term memory
- Automation system

---

## Architecture

```txt
aether/
├── engine/        # AI response generation
├── memory/        # conversation + long-term memory
├── automation/    # emails, files, tasks
├── agent/         # planning + execution
├── auth/          # multi-user + security
└── ui/            # desktop frontend
