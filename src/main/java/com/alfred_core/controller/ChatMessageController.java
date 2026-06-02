package com.alfred_core.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alfred_core.entity.ChatMessage;
import com.alfred_core.service.ChatMessageService;

@RestController
@RequestMapping("/api/chats")
public class ChatMessageController {

    private final ChatMessageService chatService;

    public ChatMessageController(ChatMessageService chatService) {
        this.chatService = chatService;
    }

//    @PostMapping
//    public ChatMessage saveChat(@RequestBody ChatMessage request,boolean isUser) {
//
//        return chatService.saveMessage(
//                request.getChatContent(),
//                isUser
//        );
//    }
    
    @GetMapping
    public List<ChatMessage> getAllChats() {
        return chatService.getAllChats();
    }
    
}