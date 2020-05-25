package kr.co.teamhash.config;

import kr.co.teamhash.account.AccountService;
import kr.co.teamhash.account.RememberMeUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor 
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AccountService accountService;
    private final DataSource dataSource;
    private final RememberMeUserDetailsService rememberMeUserDetailsService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/","/sign-up", "/check-email-token", "/login").permitAll() // 인증 허가
                .mvcMatchers(HttpMethod.GET, "/profile/**").permitAll() // `/profile/*`은 get만 허가
                .anyRequest().authenticated();


        http.formLogin()// 로그인 폼 사용
                .loginPage("/login")
                .defaultSuccessUrl("/main") // 로그인 성공시 main 호출
                .permitAll();

        http.logout()
            .logoutSuccessUrl("/");

        http.rememberMe()
                .userDetailsService(rememberMeUserDetailsService)
                .tokenRepository(tokenRepository());

}

    @Bean
    public PersistentTokenRepository tokenRepository(){
        // jdbc 기반의 tokenRepository 구현체
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .mvcMatchers("/node_modules/**")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
        // 스태틱 리소스들은 시큐리티 적용 x
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 스프링 시큐리티가 사용할 기본 AuthManager 에 이메일로만 로그인하는 UserDetailsService 를 설정하는 코드
        auth.userDetailsService(accountService);
    }
}



