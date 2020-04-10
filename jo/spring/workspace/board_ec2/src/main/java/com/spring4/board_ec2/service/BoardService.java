package com.spring4.board_ec2.service;

import java.util.List;

import com.spring4.board_ec2.domain.BoardVO;

public interface BoardService {

		public List<BoardVO> list() throws Exception;
		
		// 게시물 작성
		public void write(BoardVO vo) throws Exception;
	
}
