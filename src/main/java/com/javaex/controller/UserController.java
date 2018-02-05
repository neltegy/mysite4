package com.javaex.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.service.UserService;
import com.javaex.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/loginform",method=RequestMethod.GET)
	public String loginform() {
		
		return "user/loginform";
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(@RequestParam("email") String email,
						@RequestParam("password") String password,
						HttpSession session
			) {
		
		UserVo authUser = userService.login(email, password);
		
		
		if(authUser != null) { //로그인했을경우
			session.setAttribute("authUser", authUser);
			return "main/index";
		}else {
			return "redirect:/user/loginform?result=fail";
		}
	}
	
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public String logout(HttpSession session) {
		
		session.removeAttribute("authUser");
		return "redirect:/main";
	}
	
	@RequestMapping(value="/joinform",method=RequestMethod.GET)
	public String joinform() {
		
		return "user/joinform";
	}
	
	@RequestMapping(value="/join",method=RequestMethod.POST)
	public String join(@ModelAttribute UserVo uservo) {
		
		userService.join(uservo);
		
		return "user/joinsuccess";
	}
	
	@RequestMapping(value="/modifyform",method=RequestMethod.GET)
	public String modifyform(Model model,HttpSession session) {
		System.out.println("/modifyform");
		UserVo authUser = (UserVo) session.getAttribute("authUser"); //로그인된 사용자로부터 정보를 받아온다
		UserVo uservo = userService.bringinfo(authUser.getNo());
		model.addAttribute("userVo", uservo);
		
		return "user/modifyform";
	}
	
	@RequestMapping(value="/modify",method=RequestMethod.POST)
	public String modify(HttpSession session,@ModelAttribute UserVo uservo) {
		System.out.println("/modify");
		
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		uservo.setNo(authUser.getNo());
		userService.modify(uservo);
		//name,password,gender
		
		return "redirect:/main";
	}
	
	
}
