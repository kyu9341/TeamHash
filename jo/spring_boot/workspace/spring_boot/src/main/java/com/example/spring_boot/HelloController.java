package com.example.spring_boot;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {

	@RequestMapping("/")
	public String index() {
		return "Greetings from Spring Boot! good";
	}

	@RequestMapping("/hello")
	public String hello() {
		return "hello";
	}

}