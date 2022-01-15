package com.webchat.main.stomp.configuration;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;

import com.webchat.main.stomp.vo.WebSocketSessionInfo;


/*
  WebSocketMessageBrokerConfigurer	
  웹소켓 클라이언트로부터 simple 메시지 프로토콜을 다루는 메시지 설정(config)
 */


@Configuration
@EnableWebSocketMessageBroker
public class StompWebSocketConfig implements WebSocketMessageBrokerConfigurer {

	public static Map<String,WebSocketSessionInfo> sessionMap = new HashMap<String,WebSocketSessionInfo>();
	
	//웹소켓 클라이언트에서 수신하고 웹소켓 클라이언트로 보낸 메시지 처리와 관련된 옵션을 구성한다.
	@Override
	public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
		/*
			addDecoratorFactory : WebSocketTransportRegistration에 있는 메서드로
			DecoratorFactories 배열에 DecoratorFactory를 추가한다.
			이 DecoratorFactory는 WebSocketHandlerDecoratorFactory를 매개변수로 받는데,
			WebSocketHandlerDecoratorFactory는 WebSocketHandler를 return받는 decorate를 통해
			생성할 수있다.  이때 Handler를 외부에서 생성하여 Autowired를 통해 객체를 받거나,
			아니면 WebSocketHandlerDecorator를 생성하여  afterConnectionEstablished를 구현해준다.
		*/
		registry.addDecoratorFactory(new WebSocketHandlerDecoratorFactory() {
			@Override
			public WebSocketHandler decorate(WebSocketHandler handler) {
				return new WebSocketHandlerDecorator(handler) {
					
					/* 
					  	소켓 연결시 session 객체를 실시간으로 저장한다.
					  	session에 대한 정보뿐만 아니라, 접속 횟수, 접속 상태 - 접속 으로저장한다. 
					 */
					@Override
					public void afterConnectionEstablished(WebSocketSession session) throws Exception {
						String ipAdress =session.getLocalAddress().getAddress().toString();
						if(sessionMap.get(ipAdress) != null){
							sessionMap.get(ipAdress).setConCnt(sessionMap.get(ipAdress).getConCnt()+1);
							sessionMap.get(ipAdress).setConStatus(true);
						}else {
							WebSocketSessionInfo webSocketSessionInfo = new WebSocketSessionInfo();
							webSocketSessionInfo.setSession(session);
							webSocketSessionInfo.setConCnt(1);
							webSocketSessionInfo.setConStatus(true);
							sessionMap.put(ipAdress, webSocketSessionInfo);    
						} 
						Iterator<Map.Entry<String, WebSocketSessionInfo>> iteratorE = sessionMap.entrySet().iterator();
						while(iteratorE.hasNext()) {
							Map.Entry<String, WebSocketSessionInfo> entry = (Map.Entry<String, WebSocketSessionInfo>)iteratorE.next();
							System.out.println("ip 주소 : " +entry.getValue().getSession().getLocalAddress().getAddress()+" , 접속횟수 : "+(entry.getValue().getConCnt())+"회  , "+(entry.getValue().isConStatus() ? "접속중" :"끊김"));
						}
						/*
							super.afterConnectionEstablished(session); 를 사용 안할 시 
							아래의 Exception이 발생한다.
							No decoder for session id
							StompSubProtocolHandler decode리스트에 생성된 BufferingStompDecoder 객체가 없어 예외가 발생한다.
							해당 메서드를 실행하면 BufferingStompDecoder객체 생성에 문제가 된다고 생각하고 넘어가야한다 . 
							정확히 어디서부터 문제가 생긴지 지금당장은 알 수 없다.  
						*/
						super.afterConnectionEstablished(session);
					}
					 
					/* 
				  		소켓 연결 해제시 session 객체를 실시간으로 수정한다.
				  		접속 상태 - 종료 한다. 
					*/
					
					@Override
					public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus)
							throws Exception { 
						String ipAdress =session.getLocalAddress().getAddress().toString();
						if(sessionMap.get(ipAdress) != null){
							sessionMap.get(ipAdress).setConStatus(false);
						}
						Iterator<Map.Entry<String, WebSocketSessionInfo>> iteratorE = sessionMap.entrySet().iterator();
						while(iteratorE.hasNext()) {
							Map.Entry<String, WebSocketSessionInfo> entry = (Map.Entry<String, WebSocketSessionInfo>)iteratorE.next();
							System.out.println("ip 주소 : " +entry.getValue().getSession().getLocalAddress().getAddress()+" : "+(entry.getValue().isConStatus() ? "접속중" :"끊김"));
						}

						super.afterConnectionClosed(session, closeStatus);
					}
					
				};
			}
			
		});
	}


	//STOMP ENDPOINT 를 각각 명세된 URL로 맵핑해주고 SockJS 폴백 옵션을 설정한다.
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) { 
		registry.addEndpoint("/stomp/chat").withSockJS().setHeartbeatTime(10000);
	}

	
	// 메시지 브로커 옵션 설정
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.setApplicationDestinationPrefixes("/pub"); //MessageMapping을 통해서 /pub/hello 로 보내주면 /pub/bye와 같은 요청에 대해서 분기처리한다. 
		registry.enableSimpleBroker("/channel"); //클라이언트에게 받은 메시지를 다시 보내줄  prefix이다. 클라이언트가 경로가 /channel/1 과 같이 같은 경로 인 사람들에게 메시지를 보낸다 
	}

	
}
