package com.webchat.main.websocket.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.webchat.main.websocket.handler.WebChatHandler;


/*
	EnableWebsocket 어노테이션 추가가 필요하다.
*/

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	@Autowired
	private WebChatHandler webChatHandler ;
	 

	
	/*
	  	어제 생긴 문제점 ~ 오늘까지  분명 url을 정확히 입력했는데 hanshake 404 에러가 떴다. 404 에러라는 것은 이 url을 못 찾는다는 것이다.
	  	그래서 구글링을 해보았지만 찾을 수 없었고, 결국에는 해결해냈다.
	  	이유는 websocket에 관련된 설정과 핸들러를 @SpringBootApplication 어노테이션 바깥에 설정해 놓았었는데,
	  	이는 스프링이 실행하는 컴포넌트 스캔의 영역 밖이어서 그런것이었다. 그래서 @SpringBootApplication어노테이션 하위 패키지에 다시 복붙하니 실행되었다.
	  	
	  	그다음 자바 에러였는데, 스프링부트 2.x.x기준으로 setAllowedOrigins가 더이상 * 의 오리진을 받지 않는 다는 것이다. 
	  	그래서 setAllowedOriginPatterns를 사용하면 좀더 유연한 패턴을 사용할 수 있다하여 메서드를 바꿔줬다.
	  	
	  	그다음은 400 에러로 왜 안되었나 했더니, withSockJs() 메서드 때문이었다. 클라이언트 단에서 sockJS를 사용하지 않고 브라우저를 통한
	  	websocket에 접근하니 sockjs를 사용하지 않았기때문에 에러가 나는 것이었다. 
	  	
	  	이렇게 해서 하루의 삽질이 끝났다..
	  
	 */
	
	/* 
	  소켓 핸들러 설정 및 오리진 설정, SockJs사용 설정 , URL path설정
	 */
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {    
		registry.addHandler(webChatHandler, "/test/chat").setAllowedOriginPatterns("*");    
	}

}
