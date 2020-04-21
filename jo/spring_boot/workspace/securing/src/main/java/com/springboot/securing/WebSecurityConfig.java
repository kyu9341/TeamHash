package com.springboot.securing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity // 해당 어노테이션은 Spring Security로 web security 지원을 가능하게 하고 Spring MVC integration을 제공한다.
public class WebSecurityConfig extends WebSecurityConfigurerAdapter { // WebSecurityConfigurerAdapter를 상속 받았으며 몇가지의 메서드로 Security 설정을 하고있다.
	@Override
	protected void configure(HttpSecurity http) throws Exception {//configure 메서드는 어떤 URL이 보안이 되고 어떤 URL이 보안이 되지 않는지 정의한다.
		http
			.authorizeRequests()
				.antMatchers("/", "/home").permitAll() // "/"와 "/home" 경로는 어떠한 인증도 요구하지 않는다. 다른 것들은 모두 인증되어야 한다.
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login")
				.permitAll()
				.and()
			.logout()
				.permitAll();
	}

	@Bean
	@Override
	public UserDetailsService userDetailsService() { // 메모리에 저장된 한 유저를 설정한다.
		UserDetails user =
			 User.withDefaultPasswordEncoder()
				.username("user")
				.password("password")
				.roles("USER")
				.build();

		return new InMemoryUserDetailsManager(user);
	}
}