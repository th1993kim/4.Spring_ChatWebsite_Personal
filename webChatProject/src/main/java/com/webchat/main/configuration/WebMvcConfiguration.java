package com.webchat.main.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.webchat.main.interceptor.LoggerInterceptor;
/*
  WebMvcConfigurer 인터페이스는 
  @EnableWebMvc 어노테이션을 통한 Spring MVC를 위해 java base로된 설정 파일을 
  커스터마이즈 하기위한 콜백 메서드를 정의한다.
*/
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

	/* 
	 addInterceptor 메서드를 재정의 하는데, 추가할 인터셉터를 registry에 담아준다.
	*/
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoggerInterceptor());
	}
	
}
