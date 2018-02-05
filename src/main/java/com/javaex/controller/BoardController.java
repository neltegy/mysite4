package com.javaex.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.service.BoardService;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserBoardVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardservice;
	
	@RequestMapping("/list")
	public String list(Model model,
					   @RequestParam(value="kwd",required=false) String kwd, 
					   @RequestParam(value="choice_pageno",required=false) String choice_pageno,
					   HttpSession session
						) {
		System.out.println("/list");
		model.addAttribute("authUser", session.getAttribute("authUser"));
		
		System.out.println("kwd: "+kwd);
//		System.out.println("choice_pageno: "+choice_pageno);
		
		List<UserBoardVo> list = boardservice.bringlist(); //모든 글목록을 가져옴
		
		//-----------------------------------------------------------------------------------------------------
		int postnum = list.size(); //글의 총 개수
		int total_page_num =0;
		if(postnum%10 == 0) { //글의개수가 10단위 초과될때부터 페이지를 생성하게하는 조건문
			total_page_num = postnum/10;
		}else {
			total_page_num = postnum/10 + 1;
		}
		List<Integer> page = new ArrayList<Integer>();
		for(int i = 0; i < total_page_num; i++) {
			page.add(i);
		}
		//----------------------------------------------------------------------------------------------------
		
		if(kwd != null) {
			List<UserBoardVo> vo = boardservice.bringkwdlist(kwd);
			
			model.addAttribute("userboard_vo_list", vo);
		}else {
			model.addAttribute("userboard_vo_list", list);
		}
		
		
		
		
		model.addAttribute("page", page);
		
		model.addAttribute("postnum", postnum);
		model.addAttribute("total_page_num", total_page_num);
		
		return "board/list";
	}
	
	@RequestMapping(value="/writeform",method=RequestMethod.GET)
	public String writeform() {
		System.out.println("/writeform");
		return "board/write";
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	public String delete(@RequestParam("boardno") int boardno
			
						) {
		System.out.println("/delete");
		
		boardservice.removeboard(boardno);
		
		return "redirect:/board/list";
	}
		
		
}
