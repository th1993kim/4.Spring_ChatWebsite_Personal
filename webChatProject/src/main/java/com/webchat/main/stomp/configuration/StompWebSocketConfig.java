package com.webchat.main.stomp.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;


/*
  WebSocketMessageBrokerConfigurer	
  웹소켓 클라이언트로부터 simple 메시지 프로토콜을 다루는 메시지 설정(config)
 */


@Configuration
@EnableWebSocketMessageBroker
public class StompWebSocketConfig implements WebSocketMessageBrokerConfigurer {

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
					@Override
					public void afterConnectionEstablished(WebSocketSession session) throws Exception {
						super.afterConnectionEstablished(session);
					}
				};
			}
			
		});
	}


	//STOMP ENDPOINT 를 각각 명세된 URL로 맵핑해주고 SockJS 폴백 옵션을 설정한다.
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/stomp/chat").withSockJS();
	}

	
	// 메시지 브로커 옵션 설정
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.setApplicationDestinationPrefixes("/pub"); //MessageMapping을 통해서 /pub/hello 로 보내주면 /pub/bye와 같은 요청에 대해서 분기처리한다. 
		registry.enableSimpleBroker("/channel"); //클라이언트에게 받은 메시지를 다시 보내줄  prefix이다. 클라이언트가 경로가 /channel/1 과 같이 같은 경로 인 사람들에게 메시지를 보낸다 
	}

	
}
