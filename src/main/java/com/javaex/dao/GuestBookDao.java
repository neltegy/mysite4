package com.javaex.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.GuestBookVo;

@Repository
public class GuestBookDao {
	
	@Autowired
	private SqlSession sqlsession;
	
	public List<GuestBookVo> getGuestbooklist(){
		
		return sqlsession.selectList("guestbook.getGuestbooklist");
	}
	
	public void insertGuestbook(GuestBookVo guestbookvo) {
		
		sqlsession.insert("guestbook.insertGuestbook", guestbookvo);
		
	}
	
	public void deleteGuestbook(int no,String password) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("no", no);
		map.put("password", password);
		sqlsession.delete("guestbook.deleteGuestbookByNoPassword", map);
	}
	
	public List<GuestBookVo> getGuestbooklistpage(int page){
		
		return sqlsession.selectList("guestbook.selectListBypage", page);
	}
	
	public GuestBookVo selectGuestbook(int no) {
		
		return sqlsession.selectOne("guestbook.selectGuestbookByNo", no);
	}
}
