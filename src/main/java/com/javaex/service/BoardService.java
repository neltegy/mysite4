package com.javaex.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.BoardDao;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserBoardVo;

@Service
public class BoardService {
	
	@Autowired
	private BoardDao boardDao;
	
	public List<UserBoardVo> bringlist() {
		
		return boardDao.getlist();
	}
	
	public void removeboard(int boardno) {
		
		boardDao.deleteboard(boardno);
	}
	
	public List<UserBoardVo> bringkwdlist(String kwd){
		
		return boardDao.getkwdlist(kwd);
	}
	
	public BoardVo bringboardinfoAnd_addhit(int boardno ,int hit) {
		
		boardDao.updateBoardHitByBoardno(boardno,hit+1);
		
		return boardDao.getboardByBoardno(boardno);
	}
	
	public BoardVo bringboardinfo(int boardno) {
		
		return boardDao.getboardByBoardno(boardno);
	}
	
	public void modify(BoardVo boardvo) {
		
		boardDao.updateBoardInfoByBoardno(boardvo);
	}
	
	public void writeboard(BoardVo boardvo) {
		
		boardDao.insertBoard(boardvo);
	}
	
}
