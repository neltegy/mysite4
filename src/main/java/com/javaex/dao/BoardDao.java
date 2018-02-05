package com.javaex.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.UserBoardVo;

@Repository
public class BoardDao {
	
	@Autowired
	private SqlSession sqlsession;
	
	public List<UserBoardVo> getlist(){
		
		return sqlsession.selectList("board.getBoardList");
	}
	
	public void deleteboard(int boardno) {
		
		sqlsession.delete("board.deleteBoardByBoardNo", boardno);
	}
	
	public List<UserBoardVo> getkwdlist(String kwd){
		
		return sqlsession.selectList("board.selectBoardByKwd", kwd);
	}
}
