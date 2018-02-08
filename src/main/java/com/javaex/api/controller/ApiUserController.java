package com.javaex.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javaex.service.UserService;
import com.javaex.vo.UserVo;

@Controller
public class ApiUserController {
	
	@Autowired
	private UserService userService;
	
	@ResponseBody
	@RequestMapping(value="/user/api/emailCheck",method=RequestMethod.POST)
	public boolean emailCheck(@RequestBody UserVo uservo) {
		System.out.println(uservo);
		boolean result = userService.emailcheck(uservo.getEmail());
		System.out.println(result);
		
		return result; //response body에 json으로 변환된데이터가 들어감(pom.xml jackson이 변환하는걸 해줌)
	}
	
	@ResponseBody
	@RequestMapping(value="/user/api/jsontest",method=RequestMethod.GET)
	public UserVo jsontest() {
		UserVo uservo = userService.bringinfo(1);
		System.out.println(uservo);
		return uservo;
	}
}
