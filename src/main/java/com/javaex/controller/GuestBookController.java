package com.javaex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.service.GuestBookService;
import com.javaex.vo.GuestBookVo;

@Controller
@RequestMapping("/gb")
public class GuestBookController {
	
	@Autowired
	private GuestBookService guestbookService;
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String list(Model model) {
		System.out.println("/list");
		
		List<GuestBookVo> guestbookvo = guestbookService.bringGuestbookinfo();
		model.addAttribute("list", guestbookvo);
		
		return "guestbook/list";
	}
	
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public String save(@ModelAttribute GuestBookVo guestbookvo) {
		System.out.println("/save");
		
		guestbookService.save(guestbookvo);
		
		return "redirect:/gb/list";
	}
	
	@RequestMapping(value="/deleteform",method=RequestMethod.GET)
	public String deleteform() {
		System.out.println("/deleteform");
		return "guestbook/deleteform";
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public String delete(@RequestParam("no") int no,@RequestParam("password") String password) {
		System.out.println("/delete");
		
		guestbookService.removes(no,password);
		
		return "redirect:/gb/list";
	}
	
	@RequestMapping(value="/listajax",method=RequestMethod.GET)
	public String listajax() {
		return "guestbook/listajax";
	}
	
	
}
