package com.spring4.board_ec2.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.spring4.board_ec2.dao.BoardDAO;
import com.spring4.board_ec2.domain.BoardVO;

@Service
public class BoardServiceImpl implements BoardService {

 @Inject
 private BoardDAO dao;
 
 @Override
 public List<BoardVO> list() throws Exception {

  return dao.list();
 }

@Override
public void write(BoardVO vo) throws Exception {
	// TODO Auto-generated method stub
	
	dao.write(vo);
}

}