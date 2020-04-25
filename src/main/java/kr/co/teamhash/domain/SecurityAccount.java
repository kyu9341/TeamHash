package kr.co.teamhash.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class SecurityAccount extends User {


    private static final long serialVersionUID = 1L;
    
    public SecurityAccount(Account account) {
        super(account.getNickname(),account.getPassword(), null);
        // TODO Auto-generated constructor stub
    }

 

}