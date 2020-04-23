package com.springboot.securing;

import com.springboot.securing.service.QuickGuideUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;



@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
    private QuickGuideUserDetailsService quickGuideUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/", "/home").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/auth/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .permitAll()
                    .and()
                .logout()
                    .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // add our Users for in memory authentication
        // auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
        auth.userDetailsService(quickGuideUserDetailsService);
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }


}


/*
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
*/

