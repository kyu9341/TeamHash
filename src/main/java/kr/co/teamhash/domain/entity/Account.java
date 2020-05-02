package kr.co.teamhash.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class Account {

    @Id @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String nickname;

    private String password;

    private String school;

    private boolean emailVerified;

    private String emailCheckToken;

    private LocalDateTime joinedAt;

    private String introduction;


    @Lob @Basic(fetch = FetchType.EAGER)
    private String profileImage;


    public  void generateEmailCheckToken(){
        // 이메일 확인 토큰 생성
        this.emailCheckToken = UUID.randomUUID().toString();
    }

    // 유효한 토큰인지 확인
    public boolean isValidToken(String token) {
        return this.emailCheckToken.equals(token);
    }

    public void completeSignUp(){
        this.emailVerified = true; // 인증 완료 처리
        this.joinedAt = LocalDateTime.now(); // 현재 시간 입력
    }

}
