package kr.co.teamhash.account;

import kr.co.teamhash.domain.entity.Account;
import kr.co.teamhash.domain.entity.Notification;
import kr.co.teamhash.domain.repository.AccountRepository;
import kr.co.teamhash.domain.repository.NotificationRepository;
import kr.co.teamhash.notification.NotificationService;
import kr.co.teamhash.project.ProjectService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
public class AccountController {

    private final SignUpValidator signUpValidator;
    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final NotificationRepository notificationRepository;
    private final NotificationService notificationService;
    private final ProjectService projectService;

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


    @GetMapping("/check-email")
    public String checkEmail(@CurrentUser Account account, Model model){
        if(account == null){
            model.addAttribute("error", "wrong email");
            return "account/check-email";
        }
        model.addAttribute("email", account.getEmail());
        model.addAttribute(account);
        return "account/check-email";
    }

    @GetMapping("/resend-confirm-email")
    public String resendConfirmEmail(@CurrentUser Account account, Model model){
        if(!account.canSendConfirmEmail()){ // 인증 메일을 발송한지 1시간이 되었는지 확인
            model.addAttribute("error", "인증 메일은 1시간에 한 번만 전송할 수 있습니다.");
            model.addAttribute("email", account.getEmail());
            return "account/check-email";
        }
        // 재전송 가능하다면
        accountService.sendSignUpConfirmEmail(account);
        return "redirect:/main";
    }

    @GetMapping("/profile/{nickname}")
    public String viewProfile(@PathVariable String nickname, Model model, @CurrentUser Account account) {
        Account byNickname = accountRepository.findByNickname(nickname);
        if (nickname == null) {
            throw new IllegalArgumentException(nickname + "에 해당하는 사용자가 없습니다.");
        }

        if (account != null) {
            List<Notification> notifications = notificationRepository.findAllByAccountId(account.getId());
            model.addAttribute("notifications", notifications);
        }

        model.addAttribute(byNickname);
        // 해당 get 파라미터로 넘어온 닉네임에 해당하는 Account 객체와 현재 인증된 객체를 비교
        model.addAttribute("isOwner", byNickname.equals(account));
        model.addAttribute("throughNotification", false);

        return "account/profile";
    }

    @GetMapping("/profile/{nickname}/notification") // 알림 버튼으로 넘어온 경우
    public String viewNotification(@PathVariable String nickname, Model model, @CurrentUser Account account) {
        Account byNickname = accountRepository.findByNickname(nickname);
        if (nickname == null) {
            throw new IllegalArgumentException(nickname + "에 해당하는 사용자가 없습니다.");
        }

        if (account != null) {
            List<Notification> notifications = notificationRepository.findAllByAccountId(account.getId());
            model.addAttribute("notifications", notifications);
        }

        model.addAttribute(byNickname);
        // 해당 get 파라미터로 넘어온 닉네임에 해당하는 Account 객체와 현재 인증된 객체를 비교
        model.addAttribute("isOwner", byNickname.equals(account));
        model.addAttribute("throughNotification", true);

        return "account/profile";
    }

    // 알림 수락, 거절
    @PostMapping("/profile/{nickname}/accept/{notificationId}")
    public String acceptNotification(@PathVariable("notificationId") Long notificationId, @PathVariable("nickname") String nickname,
                                      @CurrentUser Account account) {
        Optional<Notification> notification = notificationRepository.findById(notificationId);
        if (!notification.isPresent()) {
            return "redirect:/profile/" + nickname;
        }
        String projectTitle = notification.get().getProject().getTitle();
        String projectBuilder = notification.get().getProject().getBuilderNick();
        projectService.saveProjectMember(account.getNickname(), projectTitle, projectBuilder); // 수락한 경우 프로젝트 멤버로 등록
        notificationService.deleteNotification(notificationId); // 해당 알림 제거

        return String.format("redirect:/project/%s/%s/main", projectBuilder, URLEncoder.encode(projectTitle, StandardCharsets.UTF_8));
    }

    @PostMapping("/profile/{nickname}/reject/{notificationId}")
    public String rejectNotification(@PathVariable("notificationId") Long notificationId, @PathVariable("nickname") String nickname) {
        Optional<Notification> notification = notificationRepository.findById(notificationId);
        if (!notification.isPresent()) {
            return "redirect:/profile/" + nickname;
        }

        notificationService.deleteNotification(notificationId); // 해당 알림 제거

        return "redirect:/profile/" + nickname;
    }
}
