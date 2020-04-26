package kr.co.teamhash.config;


import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import kr.co.teamhash.account.UserDetailsServiceImpl;
import kr.co.teamhash.domain.repository.AccountRepository;



@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {



    AccountRepository accountrepository; // userDetailsService에 사용될 repository

    @Bean// 비밀번호 암호화
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/","/sign-up", "/check-email-token").permitAll() // 인증 허가
                .anyRequest().authenticated()
                .and()
            .formLogin()// 로그인 폼 사용
                .defaultSuccessUrl("/main") // 로그인 성공시 main 호출
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
                .permitAll();
        
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
        // 스태틱 리소스들은 시큐리티 적용 x
    }

    // @Override
    // public void configure(AuthenticationManagerBuilder auth) throws Exception {
    //     auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    // }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {//사용자 정보 비교
        auth.userDetailsService(new UserDetailsServiceImpl(accountrepository)).passwordEncoder(passwordEncoder());
       // auth.inMemoryAuthentication().withUser("test4@naver.com").password("testtest").roles("MEMBER");
        
    }

}
