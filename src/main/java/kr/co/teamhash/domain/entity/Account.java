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

    private LocalDateTime emailCheckTokenGeneratedAt;


    public void generateEmailCheckToken(){
        // 이메일 확인 토큰 생성
        this.emailCheckToken = UUID.randomUUID().toString();
        // 이메일 확인 토큰 생성 일시 기록 : 1시간에 한 번 보낼 수 있게 제한하기 위함.
        this.emailCheckTokenGeneratedAt = LocalDateTime.now();
    }

    // 유효한 토큰인지 확인
    public boolean isValidToken(String token) {
        return this.emailCheckToken.equals(token);
    }

    public void completeSignUp(){
        this.emailVerified = true; // 인증 완료 처리
        this.joinedAt = LocalDateTime.now(); // 현재 시간 입력
    }

    // 이메일 재전송이 가능한지 확인
    public boolean canSendConfirmEmail() {
        // 이메일 확인 토큰이 생성된 시간이 지금부터 한 시간이 지났는지 확인
        return this.emailCheckTokenGeneratedAt.isBefore(LocalDateTime.now().minusHours(1));
    }
}
