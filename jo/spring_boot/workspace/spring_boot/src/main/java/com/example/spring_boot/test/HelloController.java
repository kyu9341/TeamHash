package com.example.spring_boot.test;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
	/*
	@RequestMapping("/")
	public String index() {
		return "Greetings from Spring Boot! good";
	}

	@RequestMapping("/hello")
	public String hello() {
		return "hello";
	}
	*/

	@GetMapping("/test")
    public String welcome(Model model){
        model.addAttribute("name", "jsh");
        return "welcome";
    }
}