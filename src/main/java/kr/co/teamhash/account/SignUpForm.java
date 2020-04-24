package kr.co.teamhash.account;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

// 회원 가입 폼
@Data
public class SignUpForm {

    @NotBlank
    @Size(min = 3, max = 20)
    @Pattern(regexp = "^[a-z0-9_-]{3,20}$") // 정규 표현식으로 패턴 지정
    private String nickname;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 6, max = 50)
    private String password;

}
