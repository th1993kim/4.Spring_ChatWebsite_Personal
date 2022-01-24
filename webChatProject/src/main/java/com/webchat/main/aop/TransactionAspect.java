package com.webchat.main.aop;

import java.util.Collections;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.MatchAlwaysTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

@Configuration
public class TransactionAspect {
	
	private static final String AOP_TRANSACTION_METHOD_NAME = "*";
	private static final String AOP_TRANSACTION_EXPRESSION ="execution(* board..service.*Impl.*(..))";
		
	
	/*
	     전통적이고 복고적인 구현체들의 트랜잭션 매니저를 위한 마커 인터페이스 이다.
	     마커 인터페이스란 , 일반적인 인터페이스와 동일하지만 아무 메소드도 선언하지 않은 메소드를
	     말한다.
	 */
	@Autowired
	private TransactionManager transactionManager;
	
	/*
		using the common Spring transaction infrastructure
		일반적인 스프링 기반의 트랜잭션을 사용하는 선언형 트랜잭션 매니져를 위한 AOP 메소드 인터셉터이다.
	*/
	@Bean
	public TransactionInterceptor transactionAdvice() {
		//매우 간단한 트랜잭션 속성 구현체이다.
		MatchAlwaysTransactionAttributeSource source = new MatchAlwaysTransactionAttributeSource();
		//지정한 룰에 대하여 롤백을 야기시키는 구현체이다.
		RuleBasedTransactionAttribute transactionAttribute = new RuleBasedTransactionAttribute();
		transactionAttribute.setName(AOP_TRANSACTION_METHOD_NAME);
		transactionAttribute.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
		source.setTransactionAttribute(transactionAttribute);
		return new TransactionInterceptor(transactionManager, source);
	}
	/*
		(joinpoint를 취해 작동한다.) 어드바이스를 잡아주고,적용가능한 어드바이스를 결정해주는 필터 해주는 기본적인 인터페이스이다.   
		어드바이스란 ? 관점의 구현체로 조인포인트에 삽입되어 동작하는 것을 의미한다.
	*/
	@Bean
	public Advisor transactionAdviceAdvisor() {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression(AOP_TRANSACTION_EXPRESSION);
		return new DefaultPointcutAdvisor(pointcut,transactionAdvice());
	}
	
}
