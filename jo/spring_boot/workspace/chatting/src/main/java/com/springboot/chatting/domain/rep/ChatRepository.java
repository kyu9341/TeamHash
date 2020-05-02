package com.springboot.chatting.domain.rep;

import com.springboot.chatting.domain.entity.Chat;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ChatRepository extends JpaRepository<Chat, Long> {
    
}