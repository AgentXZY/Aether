package com.alfred_core.service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alfred_core.entity.ChatMessage;
import com.alfred_core.repository.ChatMessageRepository;

@Service
public class ChatMessageService {

	private final ChatMessageRepository chatRepo;

	public ChatMessageService(ChatMessageRepository chatRepo) {
		this.chatRepo = chatRepo;
	}

	public ChatMessage saveMessage(String content, boolean isUser) {

	    ChatMessage chatMessage = new ChatMessage();

	    chatMessage.setChatContent(content);
	    chatMessage.setTimestamp(LocalDateTime.now());
	    chatMessage.setUser(isUser);

	    try {

	        String prefix = isUser ? "User" : "Assistant";

	        String fileName = "chat-logs/" + LocalDate.now() + ".txt";

	        FileWriter writer = new FileWriter(fileName, true);

	        writer.write(
	            "[" + LocalDateTime.now() + "] "
	            + prefix
	            + ":\n"
	            + content
	            + "\n\n"
	        );

	        writer.close();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    return chatRepo.save(chatMessage);
	}

	public List<ChatMessage> getAllChats() {
		return chatRepo.findAll();
	}
}