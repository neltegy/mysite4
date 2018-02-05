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
	
	public void save(GuestBookVo guestbookvo) {
		
		guestbookdao.insertGuestbook(guestbookvo);
	}
	
	public void removes(int no,String password) {
		
		guestbookdao.deleteGuestbook(no,password);
	}
}
