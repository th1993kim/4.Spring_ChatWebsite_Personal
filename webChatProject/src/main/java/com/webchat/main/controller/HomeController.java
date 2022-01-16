package com.webchat.main.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
@RestController
public class HomeController {
	static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping("/")
	public String hello() {
		return "Hello World!";
	}
	
	@RequestMapping("/chatRoom.do")
	public String chatRoom() {
		logger.debug("chatRoom.do");
		System.out.println("여기에 들어오는지는??"); 
		ModelAndView mv = new ModelAndView("/chat/chatRoom");      
		return "/chat/chatRoom"; 
	}
	
	@RequestMapping("/stompChat")
	public ModelAndView stompChat() {
		ModelAndView mv = new ModelAndView("/stomp/stompChat");
		return mv;
	}
	
}
