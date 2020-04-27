package kr.co.teamhash.domain.entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

// 로그인 시 유저 정보를 반환하기 위해 User 클래스를 확장하여 생성
public class SecurityAccount extends User {


    private static final long serialVersionUID = 1L;

    public SecurityAccount(Account account, Collection<? extends GrantedAuthority> authorities) {
        super(account.getEmail(),account.getPassword(), authorities);
        
    }



}