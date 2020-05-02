package com.springboot.chatting.service;

import com.springboot.chatting.domain.entity.Chat;
import com.springboot.chatting.domain.rep.ChatRepository;
import com.springboot.chatting.dto.ChatDto;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ChatService {
    
    private ChatRepository chatRepository;


    public ChatService(ChatRepository chatRepository){
        this.chatRepository = chatRepository;
    }

    @Transactional
    public  Long saveChat(ChatDto chatDto){
        return chatRepository.save(chatDto.toEntity()).getId();
    }

}