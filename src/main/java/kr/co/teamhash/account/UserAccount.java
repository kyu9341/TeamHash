package kr.co.teamhash.account;

import java.util.List;

import kr.co.teamhash.domain.entity.Account;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

// 로그인 시 유저 정보를 반환하기 위해 User 클래스를 확장하여 생성
@Getter
public class UserAccount extends User {

    private Account account;
    // 스프링 시큐리티가 다루는 유저 정보를 우리가 가지고 있는 도메인의 유저 정보와 연동
    public UserAccount(Account account) {
        super(account.getEmail(), account.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
        this.account = account;
    }

}