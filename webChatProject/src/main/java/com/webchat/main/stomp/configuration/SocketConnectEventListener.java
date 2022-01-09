package com.webchat.main.stomp.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.WebSocketMessageBrokerStats;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

@Component
public class SocketConnectEventListener implements ApplicationListener<SessionConnectedEvent> {

	
	//스프링에서 제공하는 Stomp 메시지 브로커 상태관련 객체
	@Autowired
	private WebSocketMessageBrokerStats stats; 
	
	@Override
	public void onApplicationEvent(SessionConnectedEvent event) {
		System.out.println(event.getTimestamp() +" : "+stats );
		
	}

}
