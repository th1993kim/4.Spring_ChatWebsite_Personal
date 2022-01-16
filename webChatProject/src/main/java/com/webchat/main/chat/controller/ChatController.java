package com.webchat.main.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.webchat.main.chat.service.ChatService;

/*
 	채팅방, 회원 관련 컨트롤러
 */
@RestController
public class ChatController {
	
	@Autowired
	private ChatService chatService;
	
	
	
	
	
}
