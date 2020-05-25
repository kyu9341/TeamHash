package kr.co.teamhash.account;

import kr.co.teamhash.domain.entity.Account;
import kr.co.teamhash.domain.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RememberMeUserDetailsService implements UserDetailsService {
// 로그인 유지 기능 사용 시 닉네임을 통해 데이터베이스를 조회하여 사용자 정보를 가져오기 위한 UserDetailsService 의 구현체
    private final AccountRepository accountRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {

        Account byNickname = accountRepository.findByNickname(nickname);

        if (byNickname == null) {
            throw new UsernameNotFoundException(nickname);
        }
        return new UserAccount(byNickname);

    }
}
