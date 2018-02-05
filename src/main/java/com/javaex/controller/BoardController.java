package com.javaex.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.service.BoardService;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserBoardVo;
import com.javaex.vo.UserVo;

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
		
		List<UserBoardVo> list = boardservice.bringlist(); //모든 글목록을 가져옴
		
		
		if(kwd != null) { // 검색을 한 경우
			kwd = "%"+kwd+"%";
			List<UserBoardVo> vo = boardservice.bringkwdlist(kwd);
			
			model.addAttribute("userboard_vo_list", vo);
		}else { // 검색을 안한 경우
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
			if(choice_pageno == null || choice_pageno.equals("1") ) { //페이지번호를 안누른경우
				List<UserBoardVo> firstvo = list.subList(0, 10); //뒤의 index는 전값까지 넣어진다.
				model.addAttribute("userboard_vo_list", firstvo); //list배열 최근꺼 10개
				
				List<Integer> fivepage = null;
				if(total_page_num > 5) {
					fivepage = page.subList(0, 5);
					model.addAttribute("page", fivepage);
				}else {
					model.addAttribute("page", page);
				}
				
			}else { //페이지번호를누른경우
				int choice_pageno_int = Integer.parseInt(choice_pageno);
				int pageindex = 0;
				int cnt = 0 ;
				
				List<Integer> fivepage = null;
				
				for(int i = 0 ; i < total_page_num/5 + 1; i++) {
					if(i*5+1 <= choice_pageno_int && choice_pageno_int <= i*5+5) { // 첫번째 돌때 1 <= choice_pageno_int <= 5
																					// 두번째 돌때 6 <= choice_pageno_int <= 10
						pageindex = cnt;
						break;
					}else {
						cnt++;
					}
				}
				
				if(pageindex*5+5 > total_page_num) {
					fivepage = page.subList(pageindex*5, total_page_num);
				}else {
					fivepage = page.subList(pageindex*5, pageindex*5+5);
				}
				
				model.addAttribute("page", fivepage);
				System.out.println("postnum"+postnum);
				List<UserBoardVo> elsevo = null;
				if(total_page_num == choice_pageno_int) {
					elsevo = list.subList(10*(choice_pageno_int-1), postnum);//12개인경우19개까지읽으면빈곳을읽기때문에글의갯수를넣었다
				}else {
					elsevo = list.subList(10*(choice_pageno_int-1), (10*(choice_pageno_int-1))+10);
				}
				
				model.addAttribute("countpage", choice_pageno_int);
				model.addAttribute("userboard_vo_list", elsevo);
			}
			
			//-----------------------------------------------------------------------------------------------
			model.addAttribute("postnum", postnum); //글의 총 개수
			model.addAttribute("total_page_num", total_page_num); //총 페이지 개수
		}
		
		return "board/list";
	}
	
	@RequestMapping(value="/writeform",method=RequestMethod.GET)
	public String writeform(HttpSession session,
							Model model) {
		System.out.println("/writeform");
		
		model.addAttribute("authUser", session.getAttribute("authUser"));
		
		return "board/write";
	}
	
	@RequestMapping(value="/write",method=RequestMethod.POST)
	public String write(@ModelAttribute BoardVo boardvo
						) {
		System.out.println("/write");

		boardservice.writeboard(boardvo);
		
		return "redirect:/board/list";
	}
	
	
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	public String delete(@RequestParam("boardno") int boardno
						) {
		System.out.println("/delete");
		
		boardservice.removeboard(boardno);
		
		return "redirect:/board/list";
	}
	
	@RequestMapping(value="/view",method=RequestMethod.GET)
	public String view(Model model,
						   HttpSession session,
						   @RequestParam("boardno") int boardno,
						   @RequestParam(value="hit",required=false, defaultValue="-1") int hit
						   ) {
		System.out.println("/view");
		model.addAttribute("authUser", session.getAttribute("authUser"));
		
		BoardVo boardvo = boardservice.bringboardinfoAnd_addhit(boardno,hit);
		
		model.addAttribute("boardvo", boardvo);
		
		return "board/view";
	}
	
	@RequestMapping(value="/modifyform")
	public String modifyform(@RequestParam("boardno") int boardno,
							 Model model
							) {
		System.out.println("/modifyform");
		
		BoardVo boardvo = boardservice.bringboardinfo(boardno);
		
		model.addAttribute("boardvo", boardvo);
		
		return "board/modify";
	}
	
	
	@RequestMapping(value="/modify",method=RequestMethod.POST)
	public String modify(@ModelAttribute BoardVo boardvo) {
		System.out.println("/modify");
		
		boardservice.modify(boardvo);
		
		return "redirect:/board/view?boardno="+boardvo.getNo();
	}
	
	@RequestMapping(value="/chat",method=RequestMethod.GET)
	public String chat() {	
		System.out.println("/chat");
		
		return "chat/chat";
	}
}
