package com.springboot.jpaboard.controller;

import com.springboot.jpaboard.dto.BoardDto;
import com.springboot.jpaboard.service.BoardService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BoardController {


private BoardService boardService;

public BoardController(BoardService boardService){
    this.boardService = boardService;
}

    @GetMapping("/")
    public String list(){
        return "board/list";
    }

    @GetMapping("/post")
    public String write(){
        return "board/write";
    }

    @PostMapping("/post")
    public String write(BoardDto boardDto){
        boardService.savePost(boardDto);
        return "redirect:/";
    }
}