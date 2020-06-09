package kr.co.teamhash.project.chat;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import kr.co.teamhash.domain.entity.Account;
import kr.co.teamhash.domain.entity.Chat;
import kr.co.teamhash.domain.repository.ChatRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {
    private ChatRepository chatRepository;


    //채팅 저장
    @Transactional
    public void saveChat(Chat chat, Account account){
        chat.setSender(account);
        chatRepository.save(chat);
    }

    //채팅 목록 가져오기
    @Transactional
    public List<Chat> getChatList(Long projectId){
        List<Chat> chattings = chatRepository.findByProjectId(projectId);

        return chattings;
    }

}