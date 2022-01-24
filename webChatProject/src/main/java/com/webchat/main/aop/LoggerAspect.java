package com.webchat.main.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/*
	Aspect어노테이션을 써서, 공통으로 처리될 기능을
	스프링 메서드에 접근하여 공통 기능을 정의한다. 
*/
@Component
@Aspect //AOP 설정 어노테이션
public class LoggerAspect {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	/* 
	 	Around 어노테이션을 통해 해당 기능이 실행될 시점, 즉 어드바이스를 정의한다.
	 	어드바이스는 다섯 종류가 있는데, Around는 메서드의 실행 전후 또는 예외발생 시점에 사용하는 어드바이스이다. (범용적이다.)
	 	execution은 포인트컷 표현식으로 적용할 메서드를 명시할 때 사용된다.
	 	execution 표현식 : ..은 0개이상의 의미, *Controller는 Controller로 끝나는 모든 클래스의 *(..) << 파라미터가 0 개이상인 모든 메서드
	 */
	@Around("execution(* com.webchat.main..controller.*Controller.*(..))")
	public Object logPrint(ProceedingJoinPoint joinPoint) throws Throwable{
		/*
			Before, After Returning, After Throwing, After 어드바이스는 JoinPoint 를 매개변수로 사용하고,
			Around 어드바이스에서만 ProceedingJoinPoint 를 매개변수로 사용한다는 점!
			Around 어드바이스에서만 proceed 메서드가 필요하기 때문이다.
			joinPoint signature 필드에는 메서드에 대한 정보가 담겨져있다.
			joinPoint.getSignature().getDeclaringTypeName() : 해당 메서드가 선언된 클래스의 이름을 호출
			joinPoint.getSignature().getName() : 해당 메서드 이름을 호출
		*/
		String type = "";
		String name = joinPoint.getSignature().getDeclaringTypeName();
		if(name.indexOf("Controller")>-1) {
			type ="Controller \t : " ;
		}
		log.debug(type+name+"."+joinPoint.getSignature().getName() + "()");
		return joinPoint.proceed();
	}
	
	

}
