package com.springboot.jpaboard.dto;

import java.time.LocalDateTime;

import com.springboot.jpaboard.domain.entity.Board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardDto {
        
    private Long id;

    private String writer;

    private String title;

    private String content;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;


    public Board toEntitiy(){
        Board build = Board.builder()
        .id(id)
        .writer(writer)
        .title(title)
        .content(content)
        .build();

        return build;
    }

    @Builder
    public BoardDto(Long id, String title, String content, String writer, LocalDateTime createdDate, LocalDateTime modifiedDate){
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;

    }

}
