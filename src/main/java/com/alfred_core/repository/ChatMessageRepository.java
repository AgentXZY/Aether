package com.alfred_core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alfred_core.entity.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

}
