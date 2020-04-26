package kr.co.teamhash.account;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kr.co.teamhash.domain.entity.Account;
import kr.co.teamhash.domain.entity.Role;
import kr.co.teamhash.domain.entity.SecurityAccount;
import kr.co.teamhash.domain.repository.AccountRepository;
import lombok.RequiredArgsConstructor;

//유저 정보 인증에 사용되는 UserDetailsService 구현
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    
    @Autowired
    AccountRepository accountRepository; //회원 정보를 조회할 repository 생성

    public UserDetailsServiceImpl(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("-----------------------------------");
        System.out.println("find user :"+email);
        System.out.println("-----------------------------------");
       
   

        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("no user"));
       String password = account.getPassword();

       System.out.println("-----------------------------------");
       System.out.println("user password:");
       System.out.println("-----------------------------------");
      
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        grantedAuthorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
        
        
        return new SecurityAccount(account,grantedAuthorities);
        
    }

    
}