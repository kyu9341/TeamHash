package kr.co.teamhash.main;

import lombok.RequiredArgsConstructor;
import org.dom4j.rule.Mode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping(value="/main")//로그인 한 뒤 처음 보는 페이지
    public String main(Principal principal, Model model) {
        model.addAttribute("name", principal.getName());
        return "main";
    }
}
