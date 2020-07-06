package kr.co.teamhash;

import kr.co.teamhash.account.AccountService;
import kr.co.teamhash.account.SignUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

@RequiredArgsConstructor
public class WithAccountSecurityContextFactory implements WithSecurityContextFactory<WithAccount> {

    private final AccountService accountService;

    @Override
    public SecurityContext createSecurityContext(WithAccount withAccount) {
        // 시큐리티 컨텍스트를 만들어서 리턴
        String nickname = withAccount.value();
        String email = nickname + "@email.com";

        // User 생성 처리
        SignUpForm signUpForm = new SignUpForm();
        signUpForm.setNickname(nickname);
        signUpForm.setEmail(email);
        signUpForm.setPassword("123456");
        accountService.processNewAccount(signUpForm);

        // 유저를 만들고 로딩
        UserDetails principal = accountService.loadUserByUsername(email);
        Authentication auth = new UsernamePasswordAuthenticationToken(principal, "password", principal.getAuthorities());

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);
        return context;
    }
}
