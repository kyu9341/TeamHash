package kr.co.teamhash.domain.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class SecurityAccount extends User {


    private static final long serialVersionUID = 1L;
 
    

    public SecurityAccount(Account account, Collection<? extends GrantedAuthority> authorities) {
        super(account.getEmail(),account.getPassword(), authorities);
        
    }



}