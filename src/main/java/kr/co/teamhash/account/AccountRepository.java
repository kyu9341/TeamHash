package kr.co.teamhash.account;

import kr.co.teamhash.domain.Account;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    Optional<Account> findByNickname(String nickname);//로그인 시 nickname을 통해 유저 정보를 가져옴
}
