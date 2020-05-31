package kr.co.teamhash.account;

import kr.co.teamhash.domain.entity.Account;
import kr.co.teamhash.domain.repository.AccountRepository;
import kr.co.teamhash.settings.Profile;
import kr.co.teamhash.settings.form.NicknameForm;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.validation.Valid;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final JavaMailSender javaMailSender;

    public Account processNewAccount(SignUpForm signUpForm){
        Account newAccount = saveNewAccount(signUpForm);

        // 이메일 확인 토큰 생성
        newAccount.generateEmailCheckToken();
        sendSignUpConfirmEmail(newAccount);
        return newAccount;
    }


    private Account saveNewAccount(@ModelAttribute @Valid SignUpForm signUpForm){
        // 회원 저장
        Account account = Account.builder()
                .email(signUpForm.getEmail())
                .nickname(signUpForm.getNickname())
                .password(passwordEncoder.encode(signUpForm.getPassword()))//password encode
                .build();

        return accountRepository.save(account);
    }

    // 회원 가입 인증 이메일을 전송하는 메소드
    public void sendSignUpConfirmEmail(Account newAccount){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(newAccount.getEmail());
        mailMessage.setSubject("Team# 회원가입 인증");
        mailMessage.setText("/check-email-token?token="+newAccount.getEmailCheckToken() + "&email="+newAccount.getEmail());
        // 이메일 전송
        javaMailSender.send(mailMessage);
    }

    public void login(Account account){
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken( // 토큰 생성
                new UserAccount(account), // Principal 객체 생성
                account.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
        // 로그인 처리
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email);//login.html form 에서 받은 email 로 회원 검색

        if(account == null){ // 이메일로 찾지 못한다면 에러를 던짐
            throw new UsernameNotFoundException(email);
        }
        return new UserAccount(account); // User 를 확장한 UserAccount 클래스에 유저 정보와 권한을 삽입하여 반환
    }

    public void completeSignUp(Account account) {
        account.completeSignUp();
        login(account); // 인증된 정보로 다시 로그인 처리
    }

    public void updateProfile(Account account, Profile profile) {

        account.setIntroduction(profile.getIntroduction());
        account.setSchool(profile.getSchool());
        account.setProfileImage(profile.getProfileImage());
        accountRepository.save(account);

    }

    public void updatePassword(Account account, String newPassword) {
        account.setPassword(passwordEncoder.encode(newPassword));
        accountRepository.save(account);
    }

    public void updateNickname(Account account, NicknameForm nicknameForm) {
        account.setNickname(nicknameForm.getNickname());
        accountRepository.save(account);
        login(account); // 변경된 닉네임으로 다시 인증처리
    }
}
