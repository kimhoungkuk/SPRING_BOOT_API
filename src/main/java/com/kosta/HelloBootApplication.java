package com.kosta;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

//API 서버를 OAuth2 인증을 받게 만들도록 함
@EnableResourceServer
// oauth2 인증 , Token 발급/관리/인증하는 프로세스를 활성화
// /oauth/token : AcessToke 발급/인증
// /oauth/authoriz : Client 인증 Token(Code) 발급/인증.
@EnableAuthorizationServer 
@SpringBootApplication
public class HelloBootApplication extends ResourceServerConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(HelloBootApplication.class, args);
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/posts/**").authenticated()
								.antMatchers("/member/**").permitAll()
								.antMatchers("/console/**").permitAll();
	}
	
	// import javax.sql.DataSource;
	@Bean
	public TokenStore jdbcTokenStore(DataSource dataSource){
		return new JdbcTokenStore(dataSource);
	}
}
