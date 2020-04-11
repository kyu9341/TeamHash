package net.sllipp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private List<User> users = new ArrayList<User>();

    @PostMapping("/create")
    public String Creater(User user){
        System.out.println(user);
        users.add(user);
        return "create";
    }

    @GetMapping("/list")
    public String list(Model model){
        model.addAttribute("users", users)
        return "list";
    }
}