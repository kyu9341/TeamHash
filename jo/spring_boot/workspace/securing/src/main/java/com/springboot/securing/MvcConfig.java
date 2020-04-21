package com.springboot.securing;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    // WebMvcConfigurer의 메서드를 오버라이드 한 것
    // 4개의 컨트롤러를 추가하였으며 두개의 컨트롤러는 home.html, hello.html 뷰를 참조했다.
    // 4번째 컨트롤러는 login.html을 참조
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/home").setViewName("home");
		registry.addViewController("/").setViewName("home");
		registry.addViewController("/hello").setViewName("hello");
		registry.addViewController("/login").setViewName("login");
	}

}