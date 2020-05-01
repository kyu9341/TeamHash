package kr.co.teamhash.account;

import kr.co.teamhash.domain.entity.Account;
import kr.co.teamhash.domain.entity.Role;
import kr.co.teamhash.domain.entity.SecurityAccount;
import kr.co.teamhash.domain.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;

    public Account processNewAccount(SignUpForm signUpForm){
        Account newAccount = saveNewAccount(signUpForm);

        return newAccount;
    }

    private Account saveNewAccount(@ModelAttribute @Valid SignUpForm signUpForm){
        // 회원 저장
        Account account = Account.builder()
                .email(signUpForm.getEmail())
                .nickname(signUpForm.getNickname())
                .password(passwordEncoder.encode(signUpForm.getPassword()))//password encode
                .build();
        Account newAccount = accountRepository.save(account);

        return newAccount;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email);//login.html form에서 받은 email로 회원 검색
        if(account == null){
            throw new UsernameNotFoundException("not found "+email);//해당 이메일이 없을 때 throw
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>(); // 유저의 권한 삽입
        // 유저 정보를 넘기기 위해 임시적으로 생성한 것
        // 이후 수정 필요
        grantedAuthorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));

        return new SecurityAccount(account,grantedAuthorities); // User를 확장한 SecurityAccount클래스에 유저 정보와 권한을 삽입하여 반환
    }
}
