package com.springboot.chatting.dto;

import lombok.*;

import java.time.LocalDateTime;


import com.springboot.chatting.domain.entity.Chat;


@Getter 
@Setter
@ToString
@NoArgsConstructor
public class ChatDto {


	
	private Long id;

    private Long projectId;


	private String sender;

    private String message;

    private LocalDateTime sendDateTime;
    
    public Chat toEntity(){
        Chat build = Chat.builder()
 
        .id(id)
        .projectId(projectId)
        .sender(sender)
        .message(message)
        .sendDateTime(sendDateTime)
        .build();

        return build;
    }


    @Builder
    public ChatDto(Long projectId, Long id, String sender, String message, LocalDateTime sendDateTime){
        this.projectId = projectId;
        this.id = id;
        this.sender = sender;
        this.message = message;
        this.sendDateTime = sendDateTime;
    }

 

}