package com.alfred_core.service;

import java.util.Collections;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.alfred_core.entity.ChatMessage;
import com.alfred_core.repository.ChatMessageRepository;

@Service
public class ConversationContextService {

	private final ChatMessageRepository chatMessageRepository;

	public ConversationContextService(ChatMessageRepository chatMessageRepository) {
		this.chatMessageRepository = chatMessageRepository;
	}

	public String getRecentHistory(int limit) {

		List<ChatMessage> recentMessages = chatMessageRepository
				.findAllByOrderByTimestampDesc(PageRequest.of(0, limit));

		Collections.reverse(recentMessages);

		StringBuilder historyBuilder = new StringBuilder();

		for (ChatMessage msg : recentMessages) {

			String sender = msg.isUser() ? "User" : "Assistant";

			historyBuilder.append(sender)
			.append(": ")
			.append(msg.getChatContent())
			.append("\n");
		}

		return historyBuilder.toString();
	}
}