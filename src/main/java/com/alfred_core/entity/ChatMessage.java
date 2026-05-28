package com.alfred_core.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ChatMessage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long chatId;
	private String chatContent;
	private LocalDateTime timestamp;
	
	public ChatMessage() {
	}

	public Long getChatId() {
		return chatId;
	}

	public String getChatContent() {
		return chatContent;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setChatId(Long chatId) {
		this.chatId = chatId;
	}

	public void setChatContent(String chatContent) {
		this.chatContent = chatContent;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

}