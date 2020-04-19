package com.springboot.jpaboard.service;

import javax.transaction.Transactional;

import com.springboot.jpaboard.domain.repo.BoardRepository;
import com.springboot.jpaboard.dto.BoardDto;

import org.springframework.stereotype.Service;


@Service
public class BoardService {

    private BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository){
        this.boardRepository = boardRepository;
    }

    @Transactional
    public Long savePost(BoardDto boardDto){
       return boardRepository.save(boardDto.toEntitiy()).getId();
    }
}