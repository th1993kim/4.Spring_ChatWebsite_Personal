package com.webchat.main.websocket.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/*
	 스프링의 org.springframework.web.socket.handler.TextWebSocketHandler
	 을 상속받아 구현하는 구현체 생성
	 TextWebSocketHandler은 AbstractWebSocketHandler을 상속받고 AbstractWebSocketHandler 은 WebsocketHandler를 인터페이스로 받는다.
	 TextWebSocketHandler를 들어가보면 BinaryMessage에 대해 차단을 하고 있다.
*/
@EnableWebSocket
@Component
public class WebChatHandler extends TextWebSocketHandler {

	
	
	//	서버 구동 후 웹소켓에 접속하는 사람들을 JVM 메모리 메소드 영역에 영구 보관한다. (이 데이터는 서버 재기동시 사라진다.) 
	private static List<WebSocketSession> webSocketSessionList = new ArrayList<>();

	
	
	//	WebsocketHandler 의 메서드  웹소켓 연결이 되었을 때 발생하는 메서드
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		webSocketSessionList.add(session);
		System.out.println("세션이 연결되었습니다."+session);
	} 
	
	
	//WebsocketHandler 의 메서드  웹소켓 연결이 한쪽이 끊어졌을 때 발생하는 메서드
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		webSocketSessionList.remove(session);
		System.out.println("세션이 닫혔습니다.." +session);
	}

	
	//AbstractWebSocketHandler 의 메서드 Message 가 전송되었을 때 발생하는 메서드
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String payload = message.getPayload();
		System.out.println("payload : " + payload);
		for(WebSocketSession socketSession : webSocketSessionList) {
			socketSession.sendMessage(message);
		}
	}
	
}
