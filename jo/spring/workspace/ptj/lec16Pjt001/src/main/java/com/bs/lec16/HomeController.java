package com.bs.lec16;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@RequestMapping("/")
	public String home(Model model) {
		
		return "home";
	}
	
	@RequestMapping("/success")
	public String success(Model model) {
		
		return "success";
	}
	
	@RequestMapping(value = "/fail", method = RequestMethod.GET)
	public String fail(Model model) {
		
		return "fail";
	}
	
}
