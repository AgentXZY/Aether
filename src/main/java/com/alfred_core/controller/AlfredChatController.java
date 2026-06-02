package com.alfred_core.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alfred_core.dto.ChatRequest;
import com.alfred_core.dto.ChatResponse;
import com.alfred_core.service.AlfredChatService;

@RestController
@RequestMapping("/api/chat")
public class AlfredChatController {

    private final AlfredChatService alfredChatService;

    public AlfredChatController(AlfredChatService alfredChatService) {
        this.alfredChatService = alfredChatService;
    }

    @PostMapping
    public ChatResponse ask(@RequestBody ChatRequest request) {
    	return alfredChatService.ask(request);
    }
}