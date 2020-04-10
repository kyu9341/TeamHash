package com.spring4.board_ec2;


import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring4.board_ec2.domain.BoardVO;
import com.spring4.board_ec2.service.BoardService;

@Controller
@RequestMapping("/board/*")
public class BoardController {

	 @Inject
	 private BoardService service;
	
	 @RequestMapping(value = "/list", method = RequestMethod.GET)
	 public void getList(Model model) throws Exception {
	  
	  List<BoardVO> list = null;
	  list = service.list();
	  model.addAttribute("list", list);
	 }
	 
	//게시물 작성
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public void getWirte() throws Exception {
	 
	}
	
	//게시물 작성
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String postWirte(BoardVO vo) throws Exception {
		service.write(vo);
		
		return "redirect:/board/list";
	}
}