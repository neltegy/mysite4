package com.javaex.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.GuestBookDao;
import com.javaex.vo.GuestBookVo;

@Service
public class GuestBookService {
	
	@Autowired
	private GuestBookDao guestbookdao;
	
	public List<GuestBookVo> bringGuestbookinfo() {
		
		return guestbookdao.getGuestbooklist();
	}
	
	public GuestBookVo save(GuestBookVo guestbookvo) {
		
		guestbookdao.insertGuestbook(guestbookvo);
		int no = guestbookvo.getNo();
		
		return guestbookdao.selectGuestbook(no);
	}
	
	public void removes(int no,String password) {
		
		guestbookdao.deleteGuestbook(no,password);
	}
	
	public List<GuestBookVo> bringGuestbookinfopage(int page){
		
		return guestbookdao.getGuestbooklistpage(page);
	}
	
	public void delete(int no,String password) {
		
		guestbookdao.deleteGuestbook(no, password);
	}
}
