package com.webchat.main.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
@RestController
public class HomeController {
	
	@RequestMapping("/")
	public String hello() {
		return "Hello World!";
	}
	
	@RequestMapping("/chatRoom.do")
	public ModelAndView chatRoom() {
		System.out.println("여기에 들어오는지는??"); 
		ModelAndView mv = new ModelAndView("/chat/chatRoom");      
		return mv; 
	}
	
	@RequestMapping("/stompChat")
	public ModelAndView stompChat() {
		ModelAndView mv = new ModelAndView("/stomp/stompChat");
		return mv;
	}
	
}
