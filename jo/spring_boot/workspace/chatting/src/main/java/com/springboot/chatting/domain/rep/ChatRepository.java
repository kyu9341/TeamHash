package com.springboot.chatting.domain.rep;

import java.util.Optional;

import com.springboot.chatting.domain.entity.Chat;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ChatRepository extends JpaRepository<Chat, Long> {
    
}