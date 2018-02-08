package com.javaex.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javaex.service.GuestBookService;
import com.javaex.vo.GuestBookVo;

@Controller
public class ApiGuestbookController {
	
	@Autowired
	private GuestBookService guestbookservice;
	
	@ResponseBody
	@RequestMapping(value="/gb/api/list", method=RequestMethod.POST)
	public List<GuestBookVo> apilist(@RequestParam("page") int page) {
		System.out.println(page);
		List<GuestBookVo> guestbooklist = guestbookservice.bringGuestbookinfopage(page);
		return guestbooklist;
	}
	
	@ResponseBody
	@RequestMapping(value="/gb/api/save", method=RequestMethod.POST)
	public GuestBookVo apisave(@RequestBody GuestBookVo guestbookvo) {
		GuestBookVo getguestbook = guestbookservice.save(guestbookvo);
		
		return getguestbook;
	}
	
	@ResponseBody
	@RequestMapping(value="/gb/api/delete",method=RequestMethod.POST)
	public int apidelete(@RequestBody GuestBookVo guestbookvo) {
		//삭제버튼을 누르면 no를 가지고 controller(deleteform)로 온다
		
		//controller에서 no를 가지고 모달로 간다(성공시코드에 모달을 넣음)
		
		//모달에서 패스워드 입력을받고 확인을 누르면 controller(delete)로 온다. (아이디를 가지고 on 클릭시)
		
		//삭제를 하고
		int no = guestbookvo.getNo();
		String password = guestbookvo.getPassword();
		
		guestbookservice.delete(no,password);
		
		return no;
	}
	
}
