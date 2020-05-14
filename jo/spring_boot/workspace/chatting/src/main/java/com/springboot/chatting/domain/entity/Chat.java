
package com.springboot.chatting.domain.entity;

import lombok.*;

import java.time.LocalDateTime;

import javax.persistence.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter 
@Setter
@Builder
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Chat{



	@Id @GeneratedValue
	private Long id;

    @Column(nullable = false)
    private Long projectId;

    @Column(nullable = false)
	private String sender;
	
    @Column(columnDefinition = "TEXT", nullable = false)
	private String message;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime sendDateTime;

    @Builder
    public Chat(Long projectId, Long id, String sender, String message, LocalDateTime sendDateTime){
        this.projectId = projectId;
        this.id = id;
        this.sender = sender;
        this.message = message;
        this.sendDateTime = sendDateTime;
    }

 

}