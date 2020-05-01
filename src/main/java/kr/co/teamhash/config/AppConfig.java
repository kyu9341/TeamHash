package kr.co.teamhash.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {

    @Bean// 비밀번호 인코딩
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
