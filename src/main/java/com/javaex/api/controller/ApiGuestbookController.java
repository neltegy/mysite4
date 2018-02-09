package com.javaex.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public Map<String, Object> apidelete(@RequestBody GuestBookVo guestbookvo) {
		//모달 버튼을 누른것같은 효과를주려면
		//.trigger(‘click’);
		int no = guestbookvo.getNo();
		String password = guestbookvo.getPassword();

		GuestBookVo guestbookVo = guestbookservice.getGuestbookByNo(no);
		
		String res_password = guestbookVo.getPassword();
		
		guestbookservice.delete(no,password);
		
		Map<String, Object> map = new HashMap<String,Object>();
		if(password.equals(res_password)) { //사용자 입력비번과 실제비번이 같을경우
			map.put("fail", 1);
			map.put("no", no);
			return map;
		}else { //비번이 다를경우
			map.put("fail", 0);
			return map;
		}
	}
	
}
