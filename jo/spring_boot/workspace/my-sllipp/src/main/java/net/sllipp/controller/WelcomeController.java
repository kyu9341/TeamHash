package net.sllipp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

    @GetMapping("/dotest")
    public String welcome(Model model){
        model.addAttribute("name", "jsh");
        return "welcome";
    }
}