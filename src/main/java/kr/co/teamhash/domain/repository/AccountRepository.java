package kr.co.teamhash.domain.repository;

import kr.co.teamhash.domain.entity.Account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface AccountRepository extends JpaRepository<Account, Long> {
    // 계정 생성시 중복 검사
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);

    // 로그인시 이메일로 유저 검사
    Account findByEmail(String email);
    Account findByNickname(String nickname);

}
