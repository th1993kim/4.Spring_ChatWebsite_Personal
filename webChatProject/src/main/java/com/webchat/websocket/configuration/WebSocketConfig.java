package com.webchat.websocket.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.webchat.websocket.handler.WebChatHandler;


/*
	EnableWebsocket 어노테이션 추가가 필요하다.
*/
@Configuration
@EnableWebSocket  
public class WebSocketConfig implements WebSocketConfigurer {

	public WebSocketHandler webChatHandler() {
		return new WebChatHandler();
	}
	
	/* 
	  소켓 핸들러 설정 및 오리진 설정, SockJs사용 설정 , URL path설정
	 */
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(webChatHandler(), "/chat")
				.setAllowedOrigins("*")
				.withSockJS();
	}

}
