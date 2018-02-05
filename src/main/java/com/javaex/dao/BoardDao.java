package com.javaex.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.BoardVo;
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
	
	public BoardVo getboardByBoardno(int boardno) {
		
		return sqlsession.selectOne("board.getboardByBoardno", boardno);
	}
	
	public void updateBoardHitByBoardno(int boardno,int hit) {
		
		Map<String , Object> map = new HashMap<String, Object>();
		map.put("boardno", boardno);
		map.put("hit", hit);
		
		sqlsession.update("board.updateBoardHitByBoardno", map);
	}
	
	public void updateBoardInfoByBoardno(BoardVo boardvo) {
		
		sqlsession.update("board.updateBoardInfoByBoardno", boardvo);
	}
	
	public void insertBoard(BoardVo boardvo) {
		
		sqlsession.insert("board.insertBoardByBoardVo", boardvo);
	}
}
