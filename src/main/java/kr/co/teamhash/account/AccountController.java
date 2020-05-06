package kr.co.teamhash.account;

import kr.co.teamhash.domain.entity.Account;
import kr.co.teamhash.domain.repository.AccountRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequiredArgsConstructor
public class AccountController {

    private final SignUpValidator signUpValidator;
    private final AccountRepository accountRepository;
    private final AccountService accountService;

    @InitBinder("signUpForm") // signUpForm 이라는 데이터를 받을 때 바인더를 설정
    public void initBinder(WebDataBinder webDataBinder){
        // Validator 를 추가
        // SignUpForm 의 타입과 매핑이되어 Validator 가 사용됨.
        webDataBinder.addValidators(signUpValidator);
    }

    @GetMapping("/sign-up")
    public String signUpForm(Model model){
        model.addAttribute("signUpForm", new SignUpForm());
        return "account/sign-up";
    }

    @PostMapping("/sign-up")
    public String signUpSubmit(@Valid @ModelAttribute SignUpForm signUpForm, Errors errors){
        if(errors.hasErrors()){
            return "account/sign-up";
        }

        Account account = accountService.processNewAccount(signUpForm);
        accountService.login(account); // 회원가입 시 자동 로그인
        return "redirect:/main";
    }

    @GetMapping("/check-email-token")
    public String checkEmailToken(String token, String email, Model model){
        Account account = accountRepository.findByEmail(email);
        String view = "account/checked-email";

        if(account == null){
            model.addAttribute("error", "wrong email");
            return view;
        }

        if(!account.isValidToken(token)){
            model.addAttribute("error", "wrong email");
            return view;
        }
        accountService.completeSignUp(account);
        model.addAttribute("nickname", account.getNickname());
        return view;
    }

}
