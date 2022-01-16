package com.webchat.main.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


/*
  HandlerInterceptor는 스프링 IoC에서 제공하는 Filter와 유사한 기능을 하면서 IoC내의 Bean객체를 활용할 수 있는 인터셉터이다.
  Intercept란 체육경기에서 상대편의 패스를 중간에 가로채는 것이라는 뜻이며, 실제로 스프링 내에서도 Servlet Request를 가로채게 되어있다.
*/

public class LoggerInterceptor implements HandlerInterceptor {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    
    //ServletRequest가 들어와서 Intercept가 일어나고에서 해줘야할 행동 
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		log.debug("==================Start=====================");
		log.debug("Request URI : \t" + request.getRequestURI() );
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

	//ServletReponse를 보내기전 처리해줘야할 내용
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		log.debug("====================END=====================");
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}
	 
}
