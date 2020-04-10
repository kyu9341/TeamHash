package net.sllipp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @PostMapping("/create")
    public String Creater(String userId, String password, String name, String email){
        System.out.println(userId);
        return "create";
    }
}