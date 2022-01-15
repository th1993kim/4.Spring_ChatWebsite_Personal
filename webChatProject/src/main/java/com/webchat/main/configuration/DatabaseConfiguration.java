package com.webchat.main.configuration;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@PropertySource("classpath:/application.properties")
public class DatabaseConfiguration {
	
	/* 
	 	ApplicationContext : Spring에서는 빈의 생성과 관계 설정과 같은 제어를 담당하는 IoC(Inversion Of Control)컨테이너인
	 	빈 팩토리(Bean Factory)가 존재한다. 하지만 실제로는 빈의 생성과 관계설정 외에 추가적인 기능이 필요한데, 이러한 이유로 Bean Factory를
	 	상속받아 확장한 Application Context를 주로 사용한다.
	  
	  	애플리케이션 컨텍스트는 별도의 설정 정보를 참고하고 IoC를 적용하여 빈의 생성, 관계설정 등의 제어작업을 총괄한다.
	  	컨텍스트에는 직접 오브젝트를 생성하고 관계를 맺어주는 코드는 없고, 그런 생성정보와 연관관계 정보에 대한 설정을 읽어 
	  	처리한다. 예를 들어 @Configruation과 같은 어노테이션이 대표적인 IoC의 설정정보이다.
	  	
	  	빈(Bean) 요청 시 처리 과정
	  	클라이언트에서 해당 빈을 요청하면 애플리케이션 컨텍스트는 다음과 같은 과정을 거쳐 빈을 반환한다.
	  	
	  	1. ApplicationContext는 @Configuration이 붙은 클래스들을 설정 정보로 등록해두고, @Bean이 붙은 메소드의 이름으로
	  	  빈 목록을 생성한다.
	  	  
	  	2.클라이언트가 해당 빈을 요청한다.
	  	
	  	3. ApplicationContext는 자신의 빈 목록에서 요청한 이름이 있는지 찾는다.
	  	
	  	4. ApplicationContext는 설정 클래스로부터 빈 생성을 요청하고, 생성된 빈을 돌려준다.
	  	
	  	
	 */
	
	
	@Autowired
	private ApplicationContext applicationContext;
	
	
	/* 
	 	커넥션 풀 : 애플리케이션과 데이터베이스를 연결 할 때 이를 효과적으로 관리하기 위해 사용되는 라이브러리
	 			 WAS가 실행되면서 DB와 미리 Connection(연결)을 해놓은 객체들을 POOL에 저장해 두었다가
	 			 클라이언트 요청이 들어오면 Connection을 빌려주고 처리가 끝나면 다시 POOL에 저장하는 방식을 말한다.
	 			 WAS에서는 수많은 요청이 들어오는데 이 때마다 커넥션을 생성해주면 상당한 리소스 소모가 된다. 따라서
	 			 미리 만들어 두어 대여점 처럼 빌려주는 방식으로 사용하는게 좀 더 좋은 효과를 낼 수 있다. 
	 	히카리 CP  : Common DBCP, Tomcat JDBC, ~~ 등 라이브러리보다 빠르고 안정성이 있다. 
	 */
	@Bean
	@ConfigurationProperties(prefix="spring.datasource.hikari")
	public HikariConfig hikariConfig() {
		return new HikariConfig();
	}
	
	
	/*
	 	앞에서 만든 HikariCP의 설정 파일을 이용해서 데이터베이스와 연결하는 데이터 소스를 생성한다.
	 	toString은 제대로 연결이 되어있는지 확인하기 위해 콘솔 출력을 한 것이다.
	 */
	@Bean
	public DataSource dataSource() throws Exception{
		DataSource dataSource = new HikariDataSource(hikariConfig());
		System.out.println(dataSource.toString());
		return dataSource;
	}
	
	
	/*
	 	MyBatis SqlSessionFactory : 데이터베이스와의 연결과 SQL실행에 대한 모든 것을 가진 객체이다.
	 	이 객체가 DataSource를 참조하여 MyBatis와 MySql 서버를 연동시켜준다.
	 	SqlSessionFactoryBuilder를 사용해서 생성한다. 
	  
	 */
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception{
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:/mapper/**/sql-*.xml"));
		return sqlSessionFactoryBean.getObject();
	}

	
	/*
	 	SqlSessionTemplate : SqlSession을 구현하고 코드에서  SqlSession을 대체하는 역할을 한다.
	 	SqlSessionTemplate는 쓰레드에 안전하고 여러개의 DAO나 매퍼에서 공유할 수 있다.
	 */
	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	

}
