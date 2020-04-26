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


//유저 정보 인증에 사용되는 UserDetailsService 구현
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    
    @Autowired
    private final AccountRepository accountRepository; //회원 정보를 조회할 repository 생성

    public UserDetailsServiceImpl(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
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