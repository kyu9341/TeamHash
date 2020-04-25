package kr.co.teamhash.account;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jdk.internal.net.http.common.Log;
import kr.co.teamhash.domain.Account;
import kr.co.teamhash.domain.SecurityAccount;
import lombok.RequiredArgsConstructor;

//유저 정보 인증에 사용되는 UserDetailsService 구현
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    
    private final AccountRepository accountRepository;


    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        
        // TODO Auto-generated method stub
        

        Account account = accountRepository.findByNickname(nickname)
                .orElseThrow(() -> new UsernameNotFoundException(nickname));


        
        return new SecurityAccount(account);
    }

}