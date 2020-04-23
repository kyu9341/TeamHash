package kr.co.teamhash.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AccountController {

    @GetMapping("/sign-up")
    public String signUpForm(Model model){

        return "account/sign-up";
    }
}
