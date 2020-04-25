package kr.co.teamhash.domain.repository;

import kr.co.teamhash.domain.entity.Account;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    //Account findByEmail(String email);//로그인 시 email을 통해 유저 정보를 가져옴
    Optional<Account> findByEmail(String email);

}
