package com.webchat.main.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/*
	declare @ExceptionHandler, @InitBinder, or @ModelAttribute 
	ExceptionHadnler,InitBinder,ModelAttribute를 선언한 메소드들이 여러 컨트롤러 클래스에서 공유하도록
	명세화 시키는 어노테이션
*/
public class ExceptionHandler {
	private Logger log =LoggerFactory.getLogger(this.getClass());
	 
//	//핸들러 클래스나 핸들러 메소드에서 처리할 예외를 다루기위한 어노테이션
//	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
//	public ModelAndView defulatExceptionHandler(HttpServletRequest request, Exception exception) {
//		ModelAndView mv = new ModelAndView("/error/error_default");
//		mv.addObject("exception", mv);
//		log.error("exception",exception);
//		return mv;
//	}
}
