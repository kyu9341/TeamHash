package com.springboot.chatting.service;

import com.springboot.chatting.domain.entity.Chat;
import com.springboot.chatting.domain.rep.ChatRepository;
import com.springboot.chatting.dto.ChatDto;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

@Service
public class ChatService {

    private ChatRepository chatRepository;


    public ChatService(ChatRepository chatRepository){
        this.chatRepository = chatRepository;
    }

    @Transactional // 전송한 채팅 저장
    public  Long saveChat(ChatDto chatDto){
        return chatRepository.save(chatDto.toEntity()).getId();
    }

    @Transactional // 저장된 채팅 목록 가져오기
    public List<ChatDto> getChatList(){
        List<Chat> chattings = chatRepository.findAll();
        List<ChatDto> chatDtoList = new ArrayList<>();
        



        for(Chat chat : chattings){
            ChatDto chatDto = ChatDto.builder()
            .id(chat.getId())
            .projectId(chat.getProjectId())
            .sender(chat.getSender())
            .message(chat.getMessage())
            .sendDateTime(chat.getSendDateTime())
            .build();
    
            chatDtoList.add(chatDto);
        }

        return chatDtoList;

    }

}