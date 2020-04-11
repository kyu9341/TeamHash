package com.springboot.thymeleaf.controller;

import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/hello")
public class HelloController {
    @RequestMapping(method = RequestMethod.GET)
    public String hello(Model model) {
        List<Hoge> list = Arrays.asList(new Hoge() {
            {
                id = 10;
                value = "hoge";
            }
        }, new Hoge() {
            {
                id = 20;
                value = "fuga";
            }
        }, new Hoge() {
            {
                id = 30;
                value = "piyo";
            }
        });
        model.addAttribute("hogeList", list);
        return "hello";
    }
}
